package l10n

import java.io.File

/**
 * The completeness checks of docs/LOCALIZATION_PLAN.md, section 5.2.
 * Each problem is one line that names the file or folder and the key.
 *
 * @param termPairs texts that must have the same translation in every language. Each side is "strings:<key>"
 *   for the Compose resources, or "<catalog path relative to the root>:<key>" for a String Catalog.
 */
class TranslationCheck(
    private val root: File,
    private val resources: File,
    private val catalogs: List<File>,
    private val infoPlistCatalog: File,
    private val legalFiles: File,
    private val pagesConfig: File,
    private val xcodeProject: File,
    private val termPairs: List<Pair<String, String>>,
) {
    private val problems = mutableListOf<String>()

    private val base = readStrings(File(resources, "values"))
    private val folders: List<Pair<Folder, Map<String, Entry>>> = resources.listFiles { file -> file.isDirectory && file.name.startsWith("values-") }
        .orEmpty().sortedBy { it.name }
        .mapNotNull { dir ->
            val folder = runCatching { Folder.parse(dir.name) }.getOrElse {
                problems += "${dir.name}: Compose resources cannot read this folder name. Use values-xx, values-xx-rYY or values-b+xx+Scrp."
                null
            }
            folder?.let { it to readStrings(dir) }
        }
    private val languageFolders = folders.filter { !it.first.isRegional }
    private val languageCodes = languageFolders.map { it.first.code }.toSet()

    /** The languages and the regions: iOS needs an `.lproj` folder for a region too, or it never offers that region. */
    private val appLanguages = folders.map { it.first }.filterNot { it.isPseudo }.map { it.code }.toSet()
    private val parsedCatalogs = catalogs.map(::Catalog)

    fun problems(): List<String> {
        checkPlaceholderSyntax("values", base)
        checkPluralForms("values", "en", base)
        checkLengths("values", base)
        for ((folder, strings) in folders) {
            if (folder.isPseudo) {
                problems += "${folder.name}: pseudo-locales are build output. Do not commit them."
            }
            checkKeys(folder, strings)
            checkPlaceholderSyntax(folder.name, strings)
            checkPlaceholders(folder.name, strings)
            checkPluralForms(folder.name, folder.language, strings)
            checkLengths(folder.name, strings)
            checkFontFamily(folder, strings)
            checkLegalFolder(folder, strings)
        }
        parsedCatalogs.forEach(::checkCatalog)
        checkLanguageLists()
        checkTermPairs()
        return problems
    }

    private fun checkKeys(folder: Folder, strings: Map<String, Entry>) {
        if (!folder.isRegional) {
            (base.keys - strings.keys).forEach { problems += "${folder.name}: key $it is missing." }
        } else if (folder.language !in languageCodes && folder.language != "en" && !folder.isPseudo) {
            problems += "${folder.name}: values-${folder.language} is missing; a regional folder holds only the keys that differ from it."
        }
        (strings.keys - base.keys).forEach { problems += "${folder.name}: key $it is not in values." }
        strings.values.filter { base[it.name] != null && base.getValue(it.name).isPlural != it.isPlural }.forEach {
            problems += "${folder.name}: key ${it.name} must be a ${if (base.getValue(it.name).isPlural) "plurals" else "string"} as in values."
        }
    }

    private fun checkPlaceholderSyntax(folderName: String, strings: Map<String, Entry>) {
        for (entry in strings.values) {
            if (entry.forms.values.any { UNSUPPORTED_PLACEHOLDER.containsMatchIn(it) }) {
                problems += "$folderName: key ${entry.name} has a placeholder other than %N\$s."
            }
        }
    }

    /** Each form may leave out a placeholder (Polish "one" often has no number), but together they use the same set. */
    private fun checkPlaceholders(folderName: String, strings: Map<String, Entry>) {
        for (entry in strings.values) {
            val expected = base[entry.name]?.forms?.values?.flatMap(::placeholders)?.toSet() ?: continue
            val used = entry.forms.values.map(::placeholders)
            if (entry.isPlural) {
                if (used.flatten().toSet() != expected || used.any { !expected.containsAll(it) }) {
                    problems += "$folderName: key ${entry.name} uses ${used.flatten().toSortedSet()}, values uses ${expected.toSortedSet()}."
                }
            } else if (used.single() != expected) {
                problems += "$folderName: key ${entry.name} uses ${used.single().toSortedSet()}, values uses ${expected.toSortedSet()}."
            }
        }
    }

    private fun checkPluralForms(folderName: String, language: String, strings: Map<String, Entry>) {
        val required = PLURAL_CATEGORIES[language]
        if (required == null) {
            problems += "$folderName: no plural categories for \"$language\". Add them to PLURAL_CATEGORIES in buildSrc."
            return
        }
        for (entry in strings.values.filter { it.isPlural }) {
            (required - entry.forms.keys).forEach { problems += "$folderName: plurals ${entry.name} has no \"$it\" form." }
            (entry.forms.keys - required).forEach { problems += "$folderName: plurals ${entry.name} has a \"$it\" form, which $language does not use." }
        }
    }

    /** The limit counts the filled-in text, so each placeholder counts as two characters, as most numbers do. */
    private fun checkLengths(folderName: String, strings: Map<String, Entry>) {
        for (entry in strings.values) {
            val limit = maxLength(base[entry.name]?.comment) ?: continue
            entry.forms.forEach { (quantity, text) ->
                val length = filledLength(text)
                if (length > limit) {
                    val form = if (quantity.isEmpty()) "" else " ($quantity)"
                    problems += "$folderName: key ${entry.name}$form has $length characters, the limit is $limit: \"$text\"."
                }
            }
        }
    }

    private fun checkFontFamily(folder: Folder, strings: Map<String, Entry>) {
        if ("font_family" !in base) return
        val value = strings["font_family"]?.forms?.get("") ?: return
        val expected = if (folder.language in SYSTEM_FONT_LANGUAGES) "system" else "brand"
        if (value != expected) problems += "${folder.name}: font_family must be \"$expected\" for the script of ${folder.language}."
    }

    private fun checkLegalFolder(folder: Folder, strings: Map<String, Entry>) {
        if ("legal_folder" !in base) return
        val value = strings["legal_folder"]?.forms?.get("") ?: return
        if (value != folder.code) {
            val alternative = if (folder.isRegional) ", or left out to use the folder of the language" else ""
            problems += "${folder.name}: legal_folder must be \"${folder.code}\"$alternative."
        }
    }

    private fun checkCatalog(catalog: Catalog) {
        val name = catalog.file.relativeTo(root).path
        for (key in catalog.strings.keys) {
            val limit = maxLength(catalog.comment(key))
            for (language in listOf(catalog.sourceLanguage) + appLanguages) {
                if (language != catalog.sourceLanguage && catalog.shouldTranslate(key) && catalog.needs(language)) {
                    val localization = catalog.localization(key, language)
                    if (localization == null) {
                        problems += "$name: \"$key\" has no $language translation."
                    } else if (catalog.stringUnits(localization).any { it["state"] != "translated" }) {
                        problems += "$name: \"$key\" in $language is not in the state \"translated\"."
                    }
                }
                if (limit == null) continue
                catalog.values(key, language).filter { filledLength(it) > limit }.forEach {
                    problems += "$name: \"$key\" in $language has ${filledLength(it)} characters, the limit is $limit: \"$it\"."
                }
            }
        }
    }

    /** Siri phrases exist only for the languages that Siri speaks; Spotlight and Shortcuts show the translated title. */
    private fun Catalog.needs(language: String): Boolean =
        file.name != "AppShortcuts.xcstrings" || language.substringBefore('-') in SIRI_LANGUAGES

    private fun checkLanguageLists() {
        val infoPlist = Catalog(infoPlistCatalog)
        val catalogLanguages = infoPlist.languages() - infoPlist.sourceLanguage
        val legalLanguages = legalFiles.listFiles { file -> file.isDirectory }.orEmpty().map { it.name }.toSet()
        val knownRegions = KNOWN_REGIONS.find(xcodeProject.readText())?.groupValues?.get(1).orEmpty()
            .split(',').map { it.trim().trim('"') }.filter { it.isNotEmpty() && it != "Base" && it != "en" }.toSet()
        compare("${infoPlistCatalog.relativeTo(root).path} languages", catalogLanguages)
        compare("knownRegions in ${xcodeProject.relativeTo(root).path}", knownRegions)
        // A region gets its own legal texts only where they differ from the texts of its language.
        (languageCodes - legalLanguages).forEach { problems += "docs/legal/files folders: $it is missing; the Compose resources have it." }
        (legalLanguages - appLanguages).forEach { problems += "docs/legal/files folders: $it has no folder in the Compose resources." }
        for ((folder, strings) in folders.filter { it.first.isRegional && it.first.code in legalLanguages }) {
            if ("legal_folder" in base && "legal_folder" !in strings) {
                problems += "${folder.name}: set legal_folder to \"${folder.code}\", or the app never shows docs/legal/files/${folder.code}."
            }
        }

        val documents = legalFiles.listFiles { file -> file.extension == "md" }.orEmpty().map { it.name }.toSet()
        val excluded = pagesConfig.readLines().map { it.trim().removePrefix("-").trim() }.toSet()
        for (language in legalLanguages) {
            (documents - File(legalFiles, language).list().orEmpty().toSet()).forEach { problems += "docs/legal/files/$language: $it is missing." }
            if ("legal/files/$language" !in excluded) problems += "docs/_config.yml: exclude legal/files/$language, so the website stays English."
        }
    }

    private fun compare(what: String, languages: Set<String>) {
        (appLanguages - languages).forEach { problems += "$what: $it is missing; the Compose resources have it." }
        (languages - appLanguages).forEach { problems += "$what: $it has no folder in the Compose resources." }
    }

    private fun checkTermPairs() {
        for ((first, second) in termPairs) {
            for (language in listOf("en") + appLanguages) {
                val a = text(first, language) ?: continue
                val b = text(second, language) ?: continue
                if (a != b) problems += "Glossary term in $language: $first is \"$a\", $second is \"$b\"."
            }
        }
    }

    /** The text that a user of [language] sees: a region falls back to its language, as Compose and iOS do. */
    private fun text(source: String, language: String): String? {
        val (where, key) = source.split(':', limit = 2)
        val fallback = language.substringBefore('-')
        if (where == "strings") {
            if (language == "en") return base[key]?.forms?.get("")
            return listOf(language, fallback).firstNotNullOfOrNull { code ->
                folders.firstOrNull { it.first.code == code }?.second?.get(key)?.forms?.get("")
            }
        }
        val catalog = parsedCatalogs.firstOrNull { it.file.relativeTo(root).path == where }
            ?: error("Term pair names $where, which is not a checked catalog.")
        require(key in catalog.strings) { "Term pair names \"$key\", which $where does not have." }
        return listOf(language, fallback).firstNotNullOfOrNull { catalog.values(key, it).singleOrNull() }
    }

    private companion object {
        val PLACEHOLDER = Regex("%(\\d+)\\\$s")
        val UNSUPPORTED_PLACEHOLDER = Regex("%(?!\\d+\\\$s)")
        val CATALOG_PLACEHOLDER = Regex("%(\\d+\\\$)?(lld|ld|d|@|arg)|\\$\\{\\w+}")
        val MAX_LENGTH = Regex("Max (\\d+) characters")
        val KNOWN_REGIONS = Regex("knownRegions = \\(([^)]*)\\);")

        fun placeholders(text: String): Set<String> = PLACEHOLDER.findAll(text).map { it.value }.toSet()

        fun maxLength(comment: String?): Int? = comment?.let { MAX_LENGTH.find(it) }?.groupValues?.get(1)?.toInt()

        fun filledLength(text: String): Int = CATALOG_PLACEHOLDER.replace(PLACEHOLDER.replace(text, "00"), "00").length
    }
}
