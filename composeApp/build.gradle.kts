plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose.multiplatform)
}

kotlin {
    listOf(iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            // SQLDelight's native driver uses the system SQLite; a static framework leaves the link to the app.
            linkerOpts("-lsqlite3")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared.domain)
            implementation(projects.shared.data)
            implementation(projects.shared.export)
            implementation(projects.shared.sync)
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.compose)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

compose.resources {
    packageOfResClass = "com.migratrack.app.generated.resources"
    publicResClass = false
    // GitHub Pages publishes the same legal files from docs/, so the website and the app cannot differ.
    customDirectory("iosMain", provider { layout.projectDirectory.dir("../docs/legal") })
}

val verifyTranslations = tasks.register<l10n.VerifyTranslations>("verifyTranslations") {
    description = "Checks that every translation is complete and keeps the rules of docs/LOCALIZATION_PLAN.md, section 5.2."
    group = "verification"
    root = rootProject.layout.projectDirectory
    resources = layout.projectDirectory.dir("src/commonMain/composeResources")
    catalogs.from(rootProject.fileTree("iosApp") { include("**/*.xcstrings") })
    infoPlistCatalog = rootProject.layout.projectDirectory.file("iosApp/iosApp/InfoPlist.xcstrings")
    legalFiles = rootProject.layout.projectDirectory.dir("docs/legal/files")
    pagesConfig = rootProject.layout.projectDirectory.file("docs/_config.yml")
    xcodeProject = rootProject.layout.projectDirectory.file("iosApp/iosApp.xcodeproj/project.pbxproj")
    termPairs = listOf(
        "strings:common_save_episode | iosApp/watchApp/Localizable.xcstrings:Save Episode",
        "strings:pain_scale_label | iosApp/watchApp/Localizable.xcstrings:Pain intensity",
        "strings:report_stat_pain_free_days | iosApp/watchApp/Localizable.xcstrings:Pain-free days",
        "strings:common_log_migraine_episode | iosApp/iosApp/Localizable.xcstrings:Log Migraine Episode",
        "iosApp/watchApp/Localizable.xcstrings:Log Migraine | iosApp/watchComplications/Localizable.xcstrings:Log Migraine",
        "iosApp/watchApp/Localizable.xcstrings:Log Migraine | iosApp/iosApp/Localizable.xcstrings:Log Migraine",
    )
    report = layout.buildDirectory.file("reports/verifyTranslations.txt")
}

val verifyTranslationsSelfTest = tasks.register<l10n.VerifyTranslationsSelfTest>("verifyTranslationsSelfTest") {
    description = "Runs the translation check on broken files and expects exactly the problems they contain."
    group = "verification"
    fixtures = rootProject.layout.projectDirectory.dir("buildSrc/src/fixtures/l10n")
    result = layout.buildDirectory.file("reports/verifyTranslationsSelfTest.txt")
}

tasks.named("check") { dependsOn(verifyTranslations, verifyTranslationsSelfTest) }

// Pseudo-locales for the layout checks of docs/LOCALIZATION_PLAN.md, section 5.1. Compose then reads a copy of the
// resources with the pseudo-locale folders; a resource key in two source sets would give two accessors.
// Xcode starts Gradle itself, so the switch is also an environment variable: MIGRATRACK_PSEUDO=true xcodebuild ...
val pseudoLocales = providers.gradleProperty("migratrack.pseudo").orElse(providers.environmentVariable("MIGRATRACK_PSEUDO"))
if (pseudoLocales.orNull == "true") {
    val resources = layout.projectDirectory.dir("src/commonMain/composeResources")
    val pseudoLocalize = tasks.register<l10n.PseudoLocalize>("pseudoLocalize") {
        englishStrings = resources.dir("values")
        output = layout.buildDirectory.dir("generated/pseudoLocales/folders")
    }
    val pseudoResources = tasks.register<Sync>("pseudoResources") {
        from(resources)
        from(pseudoLocalize)
        into(layout.buildDirectory.dir("generated/pseudoLocales/composeResources"))
    }
    compose.resources {
        customDirectory("commonMain", layout.dir(pseudoResources.map { it.destinationDir }))
    }
}
