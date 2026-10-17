package l10n

import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.w3c.dom.Element
import org.w3c.dom.Node

/**
 * Writes pseudo-locales from the English strings (docs/LOCALIZATION_PLAN.md, section 5.1):
 * - en-XA: accented letters, 40% longer, in brackets, for cut and untranslated text;
 * - ru-XC: Cyrillic letters, 40% longer, for the system font;
 * - th-XT: Thai letters with marks above and below, for the line height of tall scripts.
 * Placeholders stay as they are.
 */
@CacheableTask
abstract class PseudoLocalize : DefaultTask() {
    @get:InputDirectory
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val englishStrings: DirectoryProperty

    /** A Compose resources folder with one `values-*` folder for each pseudo-locale. */
    @get:OutputDirectory
    abstract val output: DirectoryProperty

    @TaskAction
    fun write() {
        val out = output.get().asFile.apply { deleteRecursively() }
        val sources = englishStrings.get().asFile.listFiles { file -> file.extension == "xml" }.orEmpty()
        for (locale in PseudoLocale.entries) {
            val folder = File(out, "values-${locale.folder}").apply { mkdirs() }
            sources.forEach { source -> File(folder, source.name).writeText(locale.transform(source)) }
        }
    }
}

internal enum class PseudoLocale(val folder: String, private val language: String) {
    Accented("en-rXA", "en") {
        override fun word(word: String) = word.map { ACCENTED[it] ?: it }.joinToString("")
    },
    Cyrillic("ru-rXC", "ru") {
        override fun word(word: String) = word.map { CYRILLIC[it] ?: it }.joinToString("")
    },
    Thai("th-rXT", "th") {
        override fun word(word: String) = word.map { if (it.isLetter()) THAI_SYLLABLES[it.code % THAI_SYLLABLES.size] else "$it" }.joinToString("")
    };

    abstract fun word(word: String): String

    fun transform(source: File): String {
        val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(source)
        val required = PLURAL_CATEGORIES.getValue(language)
        val entries = (0 until document.documentElement.childNodes.length).map(document.documentElement.childNodes::item)
        for (node in entries) {
            when {
                node is Element && node.tagName == "string" -> node.textContent = when (node.getAttribute("name")) {
                    // These keys choose the font and the legal texts, so they get the values of the pseudo language.
                    "font_family" -> if (language in SYSTEM_FONT_LANGUAGES) "system" else "brand"
                    "legal_folder" -> node.textContent
                    else -> text(node.textContent)
                }
                node is Element && node.tagName == "plurals" -> {
                    val items = (0 until node.childNodes.length).map(node.childNodes::item).filterIsInstance<Element>()
                    val one = items.firstOrNull { it.getAttribute("quantity") == "one" }?.textContent
                    val other = items.first { it.getAttribute("quantity") == "other" }.textContent
                    items.forEach(node::removeChild)
                    for (quantity in required) {
                        val item = document.createElement("item")
                        item.setAttribute("quantity", quantity)
                        item.textContent = text(if (quantity == "one" && one != null) one else other)
                        node.appendChild(item)
                    }
                }
                node.nodeType == Node.COMMENT_NODE -> node.parentNode.removeChild(node)
            }
        }
        val writer = java.io.StringWriter()
        TransformerFactory.newInstance().newTransformer().apply { setOutputProperty(OutputKeys.ENCODING, "UTF-8") }
            .transform(DOMSource(document), StreamResult(writer))
        return writer.toString()
    }

    private fun text(english: String): String {
        if (english.isEmpty()) return english
        val parts = PLACEHOLDER_OR_WORD.findAll(english).joinToString("") { match ->
            if (match.value.startsWith("%")) match.value else word(match.value)
        }
        val padding = (english.length * 0.4).toInt()
        return if (this == Thai) parts else "[$parts ${"~".repeat(padding)}]"
    }

    private companion object {
        val PLACEHOLDER_OR_WORD = Regex("%\\d+\\\$s|[^%]+|%")
        val ACCENTED = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".zip("åƀçðéƒĝĥîĵķļɱñöþǫŕšţûṽŵẋýžÅƁÇÐÉƑĜĤÎĴĶĻṀÑÖÞǪŔŠŢÛṼŴẊÝŽ").toMap()
        val CYRILLIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".zip("абцдефгхийклмнопярстувшжызАБЦДЕФГХИЙКЛМНОПЯРСТУВШЖЫЗ").toMap()

        // A consonant with a vowel or a tone mark above or below it, as Thai has them.
        val THAI_SYLLABLES = listOf("ป้", "ญู", "ฟิ", "ฎุ", "กี่", "ฏ", "ฐ์", "ปั๊", "ถึ", "ฤ")
    }
}
