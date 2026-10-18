package l10n

/**
 * The plural categories that CLDR defines for the cardinal numbers of each target language
 * (docs/LOCALIZATION_PLAN.md, section 4.6). Compose resources pick the form with the same CLDR rules.
 * A new language needs a row here before its folder passes the check.
 */
internal val PLURAL_CATEGORIES: Map<String, Set<String>> = buildMap {
    val other = setOf("other")
    val oneOther = setOf("one", "other")
    val oneManyOther = setOf("one", "many", "other")
    val oneFewOther = setOf("one", "few", "other")
    val oneFewManyOther = setOf("one", "few", "many", "other")
    listOf("id", "ja", "ko", "ms", "th", "vi", "zh").forEach { put(it, other) }
    listOf(
        "bg", "bn", "da", "de", "el", "en", "fi", "gu", "hi", "hu", "kk", "kn", "ml", "mr",
        "nb", "nl", "or", "pa", "sv", "ta", "te", "tr", "ur",
    ).forEach { put(it, oneOther) }
    listOf("ca", "es", "fr", "it", "pt").forEach { put(it, oneManyOther) }
    listOf("hr", "ro").forEach { put(it, oneFewOther) }
    listOf("be", "cs", "lt", "pl", "ru", "sk", "uk").forEach { put(it, oneFewManyOther) }
    put("he", setOf("one", "two", "other"))
    put("sl", setOf("one", "two", "few", "other"))
    put("ar", setOf("zero", "one", "two", "few", "many", "other"))
}

/** Languages whose script Plus Jakarta Sans does not have, so `font_family` must be "system" (plan, section 4.7). */
internal val SYSTEM_FONT_LANGUAGES: Set<String> = setOf(
    "ar", "be", "bg", "bn", "el", "gu", "he", "hi", "ja", "kk", "kn", "ko", "ml", "mr",
    "or", "pa", "ru", "ta", "te", "th", "uk", "ur", "zh",
)

/**
 * Target languages that Siri speaks, from the Siri section of https://www.apple.com/ios/feature-availability/
 * (read on October 18, 2026). `AppShortcuts.xcstrings` needs the Siri phrases only in these languages (plan, section 4.3).
 */
internal val SIRI_LANGUAGES: Set<String> = setOf(
    "ar", "da", "de", "en", "es", "fi", "fr", "he", "it", "ja", "ko", "ms", "nb", "nl", "pt", "ru", "sv", "th", "tr", "vi", "zh",
)

/**
 * One `values-*` folder of the Compose resources.
 * [code] is the language as iOS writes it ("pl", "zh-Hans"), or language and region for a regional folder ("fr-CA").
 */
internal data class Folder(val name: String, val language: String, val code: String, val isRegional: Boolean) {
    /** A pseudo-locale of docs/LOCALIZATION_PLAN.md, section 5.1: build output, never a real language. */
    val isPseudo: Boolean get() = code.endsWith("-XA") || code.endsWith("-XC") || code.endsWith("-XT")

    companion object {
        private val LANGUAGE = Regex("[a-z]{2,3}")
        private val REGIONAL = Regex("([a-z]{2,3})-r([A-Z]{2})")
        private val SCRIPT = Regex("b\\+([a-z]{2,3})\\+([A-Z][a-z]{3})")

        /** Null for the base folder `values`; throws for a name that Compose would not read. */
        fun parse(name: String): Folder? {
            val qualifier = name.removePrefix("values").removePrefix("-")
            if (qualifier.isEmpty()) return null
            LANGUAGE.matchEntire(qualifier)?.let { return Folder(name, qualifier, qualifier, isRegional = false) }
            REGIONAL.matchEntire(qualifier)?.let { return Folder(name, it.groupValues[1], "${it.groupValues[1]}-${it.groupValues[2]}", isRegional = true) }
            SCRIPT.matchEntire(qualifier)?.let { return Folder(name, it.groupValues[1], "${it.groupValues[1]}-${it.groupValues[2]}", isRegional = false) }
            throw IllegalArgumentException(name)
        }
    }
}
