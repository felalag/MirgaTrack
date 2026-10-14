package com.migratrack.app.feature.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.migratrack.app.feature.legal.LegalDocument
import com.migratrack.app.feature.summary.ActivityRings
import com.migratrack.app.ui.components.Card
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.components.PrimaryButton
import com.migratrack.app.ui.components.glow
import com.migratrack.app.ui.icons.AppIcons
import com.migratrack.app.ui.icons.Icon
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtRadius
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme

/** First screen before onboarding. The app has no accounts, so one button starts the setup. */
@Composable
fun WelcomeScreen(onGetStarted: () -> Unit, onOpenDocument: (LegalDocument) -> Unit) {
    val top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    BoxWithConstraints(Modifier.fillMaxSize().background(MtColors.Canvas)) {
        // Smaller rings on short screens keep the button on the first page, without a scroll.
        val ringSize = if (maxHeight < 720.dp) 168.dp else 208.dp
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .heightIn(min = maxHeight)
                .padding(top = top + MtSpacing.md, bottom = bottom + MtSpacing.md)
                .padding(horizontal = MtSpacing.screen),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(MtSpacing.lg)) {
                BrandRow()
                Card(
                    Modifier.fillMaxWidth(),
                    bordered = true,
                    contentPadding = PaddingValues(vertical = 32.dp),
                ) {
                    ActivityRings(
                        outer = 0.84f,
                        inner = 0.84f,
                        outerColor = MtColors.Pink,
                        innerColor = MtColors.Green,
                        centre = "88",
                        description = "Example biometric balance of 88",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        size = ringSize,
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
                    BasicText(
                        buildAnnotatedString {
                            append("Understand your patterns.\n")
                            withStyle(SpanStyle(color = MtColors.BrandLabel)) { append("Stay ahead of pain.") }
                        },
                        Modifier.fillMaxWidth().semantics { heading() },
                        style = MtTheme.type.largeTitle.copy(textAlign = TextAlign.Center),
                    )
                    MtText(
                        "Track symptoms, identify hidden triggers, and export your personal reports anytime.",
                        MtTheme.type.body,
                        Modifier.fillMaxWidth().padding(horizontal = MtSpacing.sm),
                        color = MtColors.TextSecondary,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Column(
                Modifier.padding(top = MtSpacing.lg),
                verticalArrangement = Arrangement.spacedBy(MtSpacing.md),
            ) {
                PrimaryButton("Get Started", onClick = onGetStarted)
                AgreementFooter(onOpenDocument)
            }
        }
    }
}

@Composable
private fun BrandRow() {
    Row(Modifier.semantics(mergeDescendants = true) {}, verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier.size(40.dp).clip(RoundedCornerShape(MtRadius.md)).background(MtColors.Control),
            contentAlignment = Alignment.Center,
        ) {
            Icon(AppIcons.Brain, tint = MtColors.PinkSoft, size = 24.dp, modifier = Modifier.glow(MtColors.Pink, 8.dp, alpha = 0.35f))
        }
        Spacer(Modifier.width(12.dp))
        Column {
            MtText("MigraTrack", MtTheme.type.title2, color = MtColors.TextPrimary)
            MtText("Personal Migraine & Trigger Tracker", MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal), color = MtColors.TextSecondary)
        }
    }
}

@Composable
private fun AgreementFooter(onOpenDocument: (LegalDocument) -> Unit) {
    val linkStyles = TextLinkStyles(SpanStyle(color = MtColors.TextBody, textDecoration = TextDecoration.Underline))
    BasicText(
        buildAnnotatedString {
            append("By continuing, you agree to ")
            withLink(LinkAnnotation.Clickable("terms", linkStyles) { onOpenDocument(LegalDocument.TermsOfService) }) {
                append(LegalDocument.TermsOfService.title)
            }
            append(" and ")
            withLink(LinkAnnotation.Clickable("privacy", linkStyles) { onOpenDocument(LegalDocument.PrivacyPolicy) }) {
                append(LegalDocument.PrivacyPolicy.title)
            }
            append(".")
        },
        Modifier.fillMaxWidth().padding(horizontal = MtSpacing.md),
        style = MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal, color = MtColors.TextSecondary, textAlign = TextAlign.Center),
    )
}
