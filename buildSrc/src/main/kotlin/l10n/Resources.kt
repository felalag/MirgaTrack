package l10n

import groovy.json.JsonSlurper
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Comment
import org.w3c.dom.Element
import org.w3c.dom.Node

/** One `<string>` or `<plurals>` of a strings file. A string has one form with the quantity "". */
internal class Entry(val name: String, val isPlural: Boolean, val forms: Map<String, String>, val comment: String?)

internal fun readStrings(folder: File): Map<String, Entry> =
    folder.listFiles { file -> file.extension == "xml" }.orEmpty().sortedBy { it.name }.flatMap(::readStringsFile).associateBy { it.name }

private fun readStringsFile(file: File): List<Entry> {
    val root = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file).documentElement
    return root.childNodes.elements().mapNotNull { element ->
        val name = element.getAttribute("name")
        when (element.tagName) {
            "string" -> Entry(name, isPlural = false, mapOf("" to element.textContent), element.precedingComment())
            "plurals" -> Entry(
                name,
                isPlural = true,
                element.childNodes.elements().filter { it.tagName == "item" }.associate { it.getAttribute("quantity") to it.textContent },
                element.precedingComment(),
            )
            else -> null
        }
    }
}

private fun org.w3c.dom.NodeList.elements(): List<Element> = (0 until length).map(::item).filterIsInstance<Element>()

private fun Element.precedingComment(): String? {
    var node: Node? = previousSibling
    while (node != null && node.nodeType == Node.TEXT_NODE && node.textContent.isBlank()) node = node.previousSibling
    return (node as? Comment)?.data
}

/** A String Catalog (`.xcstrings`) as Xcode writes it: JSON with a source language and a map of strings. */
internal class Catalog(val file: File) {
    private val json = JsonSlurper().parse(file) as Map<*, *>
    val sourceLanguage = json["sourceLanguage"] as String
    val strings = (json["strings"] as Map<*, *>).entries.associate { (key, value) -> key as String to value as Map<*, *> }

    fun comment(key: String): String? = strings[key]?.get("comment") as? String

    fun shouldTranslate(key: String): Boolean = strings[key]?.get("shouldTranslate") != false

    fun localization(key: String, language: String): Map<*, *>? = (strings[key]?.get("localizations") as? Map<*, *>)?.get(language) as? Map<*, *>

    fun languages(): Set<String> =
        strings.values.flatMap { (it["localizations"] as? Map<*, *>)?.keys.orEmpty() }.map { it as String }.toSet()

    /**
     * The texts of [key] in [language], with each plural and device variant; the key itself for an untranslated source.
     * A substitution such as `%2$#@tracked@` becomes each of its plural texts.
     */
    fun values(key: String, language: String): List<String> {
        val localization = localization(key, language) ?: return if (language == sourceLanguage) listOf(key) else emptyList()
        val substitutions = (localization["substitutions"] as? Map<*, *>).orEmpty().entries.associate { (name, substitution) ->
            name as String to variants(substitution as Map<*, *>).mapNotNull { it["value"] as? String }
        }
        return variants(localization).mapNotNull { it["value"] as? String }.flatMap { value ->
            substitutions.entries.fold(listOf(value)) { texts, (name, options) ->
                val reference = Regex("%(\\d+\\$)?#@$name@")
                texts.flatMap { text -> if (reference in text) options.map { option -> reference.replace(text) { option } } else listOf(text) }
            }
        }
    }

    /** Every `stringUnit` of a localization, also inside its variations and substitutions. */
    fun stringUnits(localization: Map<*, *>): List<Map<*, *>> =
        variants(localization) + (localization["substitutions"] as? Map<*, *>).orEmpty().values.flatMap { variants(it as Map<*, *>) }

    private fun variants(node: Map<*, *>): List<Map<*, *>> = buildList {
        (node["stringUnit"] as? Map<*, *>)?.let(::add)
        (node["variations"] as? Map<*, *>)?.values?.forEach { variation ->
            (variation as? Map<*, *>)?.values?.forEach { case -> (case as? Map<*, *>)?.let { addAll(variants(it)) } }
        }
    }
}
