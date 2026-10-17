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
import com.migratrack.app.generated.resources.legal_folder
import com.migratrack.app.generated.resources.legal_privacy_policy
import com.migratrack.app.generated.resources.legal_terms_of_service
import com.migratrack.app.ui.components.Card
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.components.Page
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme
import org.jetbrains.compose.resources.MissingResourceException
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

/** A document that the welcome screen and Settings link to. The text ships with the app, so it opens offline. */
@Composable
fun LegalScreen(document: LegalDocument, onBack: () -> Unit) {
    val text by produceState<LegalText?>(null, document) {
        value = parseLegalMarkdown(readLegalText(document))
    }
    Page(title = stringResource(document.title), onBack = onBack, spacing = MtSpacing.md) {
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

/**
 * The texts live in docs/legal, which GitHub Pages also publishes, so the app and the website show the same words.
 * A translation is in a folder for its language, for example `files/pl/privacy-policy.md`, and only the app shows it.
 */
enum class LegalDocument(val title: StringResource, val fileName: String) {
    PrivacyPolicy(Res.string.legal_privacy_policy, "privacy-policy.md"),
    TermsOfService(Res.string.legal_terms_of_service, "terms-of-service.md"),
    ;

    fun path(folder: String): String = if (folder.isEmpty()) "files/$fileName" else "files/$folder/$fileName"
}

/** The text in the language of the app strings, or the English text when the translation is missing. */
suspend fun readLegalText(document: LegalDocument): String = readLegalText(document, getString(Res.string.legal_folder))

internal suspend fun readLegalText(document: LegalDocument, folder: String): String {
    val bytes = try {
        Res.readBytes(document.path(folder))
    } catch (_: MissingResourceException) {
        Res.readBytes(document.path(""))
    }
    return bytes.decodeToString()
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
