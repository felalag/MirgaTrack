plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

// MigraCore.framework for the watch app and its complication extension. No Compose: watchOS has no Compose UI.
kotlin {
    listOf(watchosArm64(), watchosDeviceArm64(), watchosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "MigraCore"
            isStatic = true
        }
    }

    sourceSets {
        watchosMain.dependencies {
            implementation(projects.shared.domain)
            implementation(projects.shared.data)
            implementation(projects.shared.sync)
        }
    }
}
