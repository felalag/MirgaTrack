package l10n

import java.io.File
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

/**
 * Runs [TranslationCheck] on broken files and compares its problems with `expected-problems.txt`.
 * The project has no translation yet, so without this test most rules would never run.
 */
@CacheableTask
abstract class VerifyTranslationsSelfTest : DefaultTask() {
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val fixtures: DirectoryProperty

    @get:OutputFile
    abstract val result: RegularFileProperty

    @TaskAction
    fun test() {
        val dir = fixtures.get().asFile
        val actual = TranslationCheck(
            root = dir,
            resources = File(dir, "composeResources"),
            catalogs = listOf(File(dir, "App.xcstrings"), File(dir, "AppShortcuts.xcstrings")),
            infoPlistCatalog = File(dir, "InfoPlist.xcstrings"),
            legalFiles = File(dir, "legal"),
            pagesConfig = File(dir, "_config.yml"),
            xcodeProject = File(dir, "app.xcodeproj/project.pbxproj"),
            termPairs = listOf("strings:greeting" to "App.xcstrings:Hello"),
        ).problems()
        val expected = File(dir, "expected-problems.txt").readLines().filter { it.isNotBlank() }
        val missing = expected - actual.toSet()
        val unexpected = actual - expected.toSet()
        if (missing.isNotEmpty() || unexpected.isNotEmpty()) {
            throw GradleException(
                "The translation check found other problems than expected.\n" +
                    missing.joinToString("") { "Not found: $it\n" } + unexpected.joinToString("") { "Found too: $it\n" },
            )
        }
        result.get().asFile.writeText("${actual.size} expected problems found")
    }
}
