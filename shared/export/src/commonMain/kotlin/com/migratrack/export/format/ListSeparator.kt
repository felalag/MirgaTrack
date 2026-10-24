package com.migratrack.export.format

/**
 * The mark between the items of a list in [language], an ISO 639 code such as "ja" or "zh-Hant":
 * "، " in Arabic and Urdu, "、" in Chinese and Japanese, ", " in the other languages of the app.
 */
fun listSeparatorFor(language: String): String =
    // Not NSListFormatter: it puts "and" before the last item and bidi isolates around each item, which a table cell does not want.
    when (language.substringBefore('-').substringBefore('_').lowercase()) {
        "ar", "ur" -> "، "
        "zh", "ja" -> "、"
        else -> ", "
    }
