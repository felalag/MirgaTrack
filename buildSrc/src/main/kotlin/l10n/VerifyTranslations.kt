package l10n

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

/** Fails the build when a translation is incomplete or breaks a rule of docs/LOCALIZATION_PLAN.md, section 5.2. */
@CacheableTask
abstract class VerifyTranslations : DefaultTask() {
    /** The repository root, for the paths in the messages. */
    @get:Internal
    abstract val root: DirectoryProperty

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val resources: DirectoryProperty

    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val catalogs: ConfigurableFileCollection

    /** The catalog whose languages make the `.lproj` folders that iOS reads (plan, section 4.9). */
    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val infoPlistCatalog: RegularFileProperty

    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val legalFiles: DirectoryProperty

    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val pagesConfig: RegularFileProperty

    @get:InputFile
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val xcodeProject: RegularFileProperty

    /** "first | second" pairs of texts that must agree in every language; see [TranslationCheck]. */
    @get:Input
    abstract val termPairs: ListProperty<String>

    /** The problems found, one per line. Empty when the check passes. */
    @get:OutputFile
    abstract val report: RegularFileProperty

    @TaskAction
    fun verify() {
        val problems = TranslationCheck(
            root = root.get().asFile,
            resources = resources.get().asFile,
            catalogs = catalogs.files.sortedBy { it.path },
            infoPlistCatalog = infoPlistCatalog.get().asFile,
            legalFiles = legalFiles.get().asFile,
            pagesConfig = pagesConfig.get().asFile,
            xcodeProject = xcodeProject.get().asFile,
            termPairs = termPairs.get().map { pair -> pair.split('|').map(String::trim).let { it[0] to it[1] } },
        ).problems()
        report.get().asFile.writeText(problems.joinToString("\n"))
        if (problems.isNotEmpty()) {
            throw GradleException("Translations have ${problems.size} problems:\n" + problems.joinToString("\n") { "- $it" })
        }
    }
}
