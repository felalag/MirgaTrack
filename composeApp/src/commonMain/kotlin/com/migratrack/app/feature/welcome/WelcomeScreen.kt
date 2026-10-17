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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.migratrack.app.feature.legal.LegalDocument
import com.migratrack.app.feature.summary.DayStrip
import com.migratrack.app.feature.summary.StripDay
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.common_last_30_days
import com.migratrack.app.generated.resources.welcome_agreement
import com.migratrack.app.generated.resources.welcome_body
import com.migratrack.app.generated.resources.welcome_example
import com.migratrack.app.generated.resources.welcome_example_a11y
import com.migratrack.app.generated.resources.welcome_get_started
import com.migratrack.app.generated.resources.welcome_headline_doctor
import com.migratrack.app.generated.resources.welcome_headline_patterns
import com.migratrack.app.generated.resources.welcome_pain_free_days
import com.migratrack.app.generated.resources.welcome_tagline
import com.migratrack.app.ui.appendWithPlaceholders
import com.migratrack.app.ui.components.BrandMark
import com.migratrack.app.ui.components.Card
import com.migratrack.app.ui.components.MtText
import com.migratrack.app.ui.components.PrimaryButton
import com.migratrack.app.ui.components.SectionCaption
import com.migratrack.app.ui.components.contentColumn
import com.migratrack.app.ui.theme.MtColors
import com.migratrack.app.ui.theme.MtRadius
import com.migratrack.app.ui.theme.MtSpacing
import com.migratrack.app.ui.theme.MtTheme
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

/** First screen before onboarding. The app has no accounts, so one button starts the setup. */
@Composable
fun WelcomeScreen(onGetStarted: () -> Unit, onOpenDocument: (LegalDocument) -> Unit) {
    val top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    BoxWithConstraints(Modifier.fillMaxSize().background(MtColors.Canvas)) {
        // Narrower strip on short screens keeps the button on the first page, without a scroll.
        val stripPadding = if (maxHeight < 720.dp) 40.dp else 0.dp
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .heightIn(min = maxHeight)
                .padding(top = top + MtSpacing.md, bottom = bottom + MtSpacing.md)
                .contentColumn()
                .padding(horizontal = MtSpacing.screen),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(MtSpacing.lg)) {
                BrandRow()
                SampleStripCard(stripPadding)
                Column(verticalArrangement = Arrangement.spacedBy(MtSpacing.sm)) {
                    BasicText(
                        buildAnnotatedString {
                            append(stringResource(Res.string.welcome_headline_patterns))
                            append("\n")
                            withStyle(SpanStyle(color = MtColors.BrandLabel)) { append(stringResource(Res.string.welcome_headline_doctor)) }
                        },
                        Modifier.fillMaxWidth().semantics { heading() },
                        style = MtTheme.type.largeTitle.copy(textAlign = TextAlign.Center),
                    )
                    MtText(
                        stringResource(Res.string.welcome_body),
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
                PrimaryButton(stringResource(Res.string.welcome_get_started), onClick = onGetStarted)
                AgreementFooter(onOpenDocument)
            }
        }
    }
}

private val SampleDays: List<StripDay> = List(30) { day ->
    if (day in setOf(3, 9, 14, 15, 22, 27)) StripDay.Attack else StripDay.PainFree
}

@Composable
private fun SampleStripCard(stripPadding: Dp) {
    val painFree = SampleDays.count { it == StripDay.PainFree }
    val description = pluralStringResource(Res.plurals.welcome_example_a11y, painFree, painFree)
    Card(
        Modifier
            .fillMaxWidth()
            .clearAndSetSemantics { contentDescription = description },
        color = MtColors.CardRaised,
        radius = MtRadius.xl,
        bordered = true,
        contentPadding = PaddingValues(MtSpacing.md),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SectionCaption(stringResource(Res.string.common_last_30_days), Modifier.weight(1f))
            MtText(
                stringResource(Res.string.welcome_example),
                MtTheme.type.subhead.copy(fontWeight = FontWeight.SemiBold),
                Modifier.clip(CircleShape).background(MtColors.Control).padding(horizontal = 10.dp, vertical = 3.dp),
                color = MtColors.TextBody,
            )
        }
        Row(verticalAlignment = Alignment.Bottom) {
            MtText(painFree.toString(), MtTheme.type.hero, color = MtColors.TextPrimary)
            MtText(" " + pluralStringResource(Res.plurals.welcome_pain_free_days, painFree), MtTheme.type.headline, color = MtColors.TextBody, modifier = Modifier.padding(bottom = 8.dp))
        }
        Spacer(Modifier.height(MtSpacing.sm))
        DayStrip(SampleDays, Modifier.padding(horizontal = stripPadding))
    }
}

@Composable
private fun BrandRow() {
    Row(Modifier.semantics(mergeDescendants = true) {}, verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier.size(40.dp).clip(RoundedCornerShape(MtRadius.md)).background(MtColors.CanvasDeep),
            contentAlignment = Alignment.Center,
        ) {
            BrandMark(size = 24.dp)
        }
        Spacer(Modifier.width(12.dp))
        Column {
            MtText("MigraTrack", MtTheme.type.title2, color = MtColors.TextPrimary)
            MtText(stringResource(Res.string.welcome_tagline), MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal), color = MtColors.TextSecondary)
        }
    }
}

@Composable
private fun AgreementFooter(onOpenDocument: (LegalDocument) -> Unit) {
    val linkStyles = TextLinkStyles(SpanStyle(color = MtColors.TextBody, textDecoration = TextDecoration.Underline))
    val template = stringResource(Res.string.welcome_agreement)
    val terms = stringResource(LegalDocument.TermsOfService.title)
    val privacy = stringResource(LegalDocument.PrivacyPolicy.title)
    BasicText(
        buildAnnotatedString {
            // One sentence with placeholders, so a translation can put the two links in any order.
            appendWithPlaceholders(template) { index ->
                when (index) {
                    1 -> withLink(LinkAnnotation.Clickable("terms", linkStyles) { onOpenDocument(LegalDocument.TermsOfService) }) {
                        append(terms)
                    }
                    2 -> withLink(LinkAnnotation.Clickable("privacy", linkStyles) { onOpenDocument(LegalDocument.PrivacyPolicy) }) {
                        append(privacy)
                    }
                }
            }
        },
        Modifier.fillMaxWidth().padding(horizontal = MtSpacing.md),
        style = MtTheme.type.subhead.copy(fontWeight = FontWeight.Normal, color = MtColors.TextSecondary, textAlign = TextAlign.Center),
    )
}
