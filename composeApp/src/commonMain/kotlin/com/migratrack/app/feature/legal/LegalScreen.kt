package com.migratrack.app.feature.legal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import com.migratrack.app.ui.components.Card
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.components.Page
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme

/** A document that the welcome screen and Settings link to. The text ships with the app, so it opens offline. */
@Composable
fun LegalScreen(document: LegalDocument, onBack: () -> Unit) {
    Page(title = document.title, onBack = onBack, spacing = MtSpacing.md) {
        MtText("Effective $EFFECTIVE_DATE", MtTheme.type.subhead, color = MtColors.TextSecondary)
        document.sections.forEach { section ->
            Card(Modifier, bordered = true) {
                Column(verticalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
                    MtText(section.heading, MtTheme.type.headline, Modifier.semantics { heading() })
                    section.paragraphs.forEach { MtText(it, MtTheme.type.body, color = MtColors.TextBody) }
                }
            }
        }
    }
}

private const val EFFECTIVE_DATE = "October 14, 2026"

class LegalSection(val heading: String, vararg val paragraphs: String)

enum class LegalDocument(val title: String, val sections: List<LegalSection>) {
    PrivacyPolicy(
        "Privacy Policy",
        listOf(
            LegalSection(
                "Summary",
                "MigraTrack keeps your data on your iPhone and your Apple Watch. We do not collect, receive, or sell your data.",
                "The app has no account and no server. It contains no analytics and no advertising.",
            ),
            LegalSection(
                "Data on your iPhone",
                "The app stores your baseline profile, your name if you enter it, and the episodes that you log. " +
                    "The baseline profile contains your diagnoses, triggers, medications, and typical pain intensity.",
                "This data stays in a database inside the app on your iPhone.",
            ),
            LegalSection(
                "Apple Health",
                "With your permission, the app reads your sleep from Apple Health to show last night's sleep. The app writes nothing to Apple Health.",
                "Sleep data does not leave your iPhone. You can remove the permission in the Health app at any time.",
            ),
            LegalSection(
                "Apple Watch",
                "Your iPhone sends your recent episodes and your logging preset to your paired Apple Watch. The watch sends the episodes that you log on it to your iPhone.",
                "This data goes directly between your own devices through Apple's WatchConnectivity.",
            ),
            LegalSection(
                "Backups",
                "An iCloud or computer backup of your iPhone can include the MigraTrack data. Your Apple Account settings control these backups.",
            ),
            LegalSection(
                "Reports that you share",
                "A PDF or Excel report leaves the app only when you share it from the share sheet. You choose the recipient.",
            ),
            LegalSection(
                "Delete your data",
                "Settings › Sign Out of MigraTrack permanently deletes all MigraTrack data on this iPhone. When you delete the app, its data is deleted too.",
            ),
            LegalSection(
                "Changes",
                "If this policy changes, the new version shows in the app with a new effective date.",
            ),
        ),
    ),
    TermsOfService(
        "Terms of Service",
        listOf(
            LegalSection(
                "Not medical advice",
                "MigraTrack is a diary for your migraine episodes. It does not diagnose, treat, or prevent any disease, and it is not a medical device.",
                "Speak with your clinician before you change your treatment. In an emergency, call your local emergency number.",
            ),
            LegalSection(
                "Your data",
                "Your data stays on your devices. You are responsible for your backups. If you lose your iPhone without a backup, the data cannot be recovered.",
            ),
            LegalSection(
                "Use of the app",
                "You can use MigraTrack for your personal, non-commercial use.",
            ),
            LegalSection(
                "No warranty",
                "The app is provided \"as is\", without a warranty of any kind. The statistics and charts can contain errors.",
            ),
            LegalSection(
                "Limitation of liability",
                "To the extent that the law permits, the developers are not liable for loss of data or for damages that come from the use of the app.",
            ),
            LegalSection(
                "Changes",
                "If these terms change, the new version shows in the app with a new effective date.",
            ),
        ),
    ),
}
