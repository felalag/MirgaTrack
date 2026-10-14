package com.migratrack.app.feature.legal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.ui.components.Card
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.components.Page
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme

/** A document that the welcome screen and Settings link to. The text ships with the app, so it opens offline. */
@Composable
fun LegalScreen(document: LegalDocument, onBack: () -> Unit) {
    val text by produceState<LegalText?>(null, document) {
        value = parseLegalMarkdown(Res.readBytes(document.resourcePath).decodeToString())
    }
    Page(title = document.title, onBack = onBack, spacing = MtSpacing.md) {
        val loaded = text ?: return@Page
        loaded.intro.forEach { MtText(it, MtTheme.type.subhead, color = MtColors.TextSecondary) }
        loaded.sections.forEach { section ->
            Card(Modifier, bordered = true) {
                Column(verticalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
                    MtText(section.heading, MtTheme.type.headline, Modifier.semantics { heading() })
                    section.paragraphs.forEach { MtText(it, MtTheme.type.body, color = MtColors.TextBody) }
                }
            }
        }
    }
}

/** The texts live in docs/legal, which GitHub Pages also publishes, so the app and the website show the same words. */
enum class LegalDocument(val title: String, val resourcePath: String) {
    PrivacyPolicy("Privacy Policy", "files/privacy-policy.md"),
    TermsOfService("Terms of Service", "files/terms-of-service.md"),
}

class LegalSection(val heading: String, val paragraphs: List<String>)

class LegalText(val intro: List<String>, val sections: List<LegalSection>)

/**
 * Reads the small Markdown subset of the legal files: front matter, a `#` title, `##` sections, and plain paragraphs.
 * The title is skipped because the page bar shows it already.
 */
fun parseLegalMarkdown(markdown: String): LegalText {
    val intro = mutableListOf<String>()
    val sections = mutableListOf<LegalSection>()
    var paragraphs = intro
    val paragraph = StringBuilder()
    fun endParagraph() {
        if (paragraph.isNotEmpty()) paragraphs += paragraph.toString()
        paragraph.clear()
    }

    val lines = markdown.lines()
    val bodyStart = if (lines.firstOrNull()?.trim() == "---") lines.drop(1).indexOfFirst { it.trim() == "---" } + 2 else 0
    for (line in lines.drop(bodyStart).map(String::trim)) {
        when {
            line.isEmpty() -> endParagraph()
            line.startsWith("## ") -> {
                endParagraph()
                paragraphs = mutableListOf()
                sections += LegalSection(line.removePrefix("## "), paragraphs)
            }
            line.startsWith("# ") -> endParagraph()
            else -> paragraph.append(if (paragraph.isEmpty()) line else " $line")
        }
    }
    endParagraph()
    return LegalText(intro, sections)
}
