package com.migratrack.app.ui.theme

import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.font_family
import com.migratrack.app.generated.resources.plus_jakarta_sans_bold
import com.migratrack.app.generated.resources.plus_jakarta_sans_extra_bold
import com.migratrack.app.generated.resources.plus_jakarta_sans_medium
import com.migratrack.app.generated.resources.plus_jakarta_sans_regular
import com.migratrack.app.generated.resources.plus_jakarta_sans_semi_bold
import com.migratrack.app.ui.stringResource
import org.jetbrains.compose.resources.Font

/**
 * Type scale of the specification, set in Plus Jakarta Sans.
 * With [systemFont], the scripts that Plus Jakarta Sans does not have use the iOS system font (plan, section 4.7).
 */
@Immutable
class MtTypography(brandFamily: FontFamily, private val systemFont: Boolean) {
    private val base = TextStyle(fontFamily = if (systemFont) FontFamily.Default else brandFamily, color = MtColors.TextPrimary)
    private val numbers = base.copy(fontFeatureSettings = "tnum")

    /** Big statistics: attack counts, sleep hours. */
    val hero = numbers.scaled(40.sp, 48.sp, FontWeight.ExtraBold, (-0.02).em)
    val display = numbers.scaled(34.sp, 40.sp, FontWeight.Bold, (-0.02).em)
    /** Onboarding page titles. */
    val largeTitle = base.scaled(28.sp, 34.sp, FontWeight.Bold, (-0.01).em)
    val title1 = base.scaled(24.sp, 28.sp, FontWeight.Bold, (-0.01).em)
    val title2 = base.scaled(20.sp, 24.sp, FontWeight.SemiBold)
    val headline = base.scaled(17.sp, 22.sp, FontWeight.SemiBold)
    val body = base.scaled(15.sp, 20.sp, FontWeight.Normal)
    val bodyStrong = body.copy(fontWeight = FontWeight.SemiBold)
    val subhead = base.scaled(13.sp, 18.sp, FontWeight.Medium)
    val caption = base.scaled(11.sp, 14.sp, FontWeight.Bold, 0.06.em)
    val tabLabel = base.scaled(11.sp, 14.sp, FontWeight.Medium)
    val number = numbers

    /**
     * The fixed line heights and the letter spacing suit Plus Jakarta Sans only. Thai, Indic and Arabic marks
     * need the line height of the font, and letter spacing breaks the joined letters of Arabic.
     */
    private fun TextStyle.scaled(size: TextUnit, lineHeight: TextUnit, weight: FontWeight, letterSpacing: TextUnit = TextUnit.Unspecified) =
        copy(
            fontSize = size,
            lineHeight = if (systemFont) TextUnit.Unspecified else lineHeight,
            fontWeight = weight,
            letterSpacing = if (systemFont) TextUnit.Unspecified else letterSpacing,
        )
}

private val LocalTypography = staticCompositionLocalOf<MtTypography> { error("MigraTrackTheme is missing") }

object MtTheme {
    val type: MtTypography
        @Composable get() = LocalTypography.current
}

@Composable
fun MigraTrackTheme(content: @Composable () -> Unit) {
    val family = FontFamily(
        Font(Res.font.plus_jakarta_sans_regular, FontWeight.Normal),
        Font(Res.font.plus_jakarta_sans_medium, FontWeight.Medium),
        Font(Res.font.plus_jakarta_sans_semi_bold, FontWeight.SemiBold),
        Font(Res.font.plus_jakarta_sans_bold, FontWeight.Bold),
        Font(Res.font.plus_jakarta_sans_extra_bold, FontWeight.ExtraBold),
    )
    val systemFont = stringResource(Res.string.font_family) == "system"
    val typography = remember(family, systemFont) { MtTypography(family, systemFont) }
    CompositionLocalProvider(
        LocalDensity provides LocalDensity.current.withFontScaleAtMost(MaxFontScale),
        LocalTypography provides typography,
        LocalTextSelectionColors provides TextSelectionColors(MtColors.Pink, MtColors.Pink.copy(alpha = 0.3f)),
        content = content,
    )
}

/**
 * The layouts hold up to the largest standard Dynamic Type size, XXXL. The accessibility sizes above it
 * overflow the fixed chips, bars, and day strips, so text stops growing there.
 */
private const val MaxFontScale = 1.35f

/** Keeps text at its design size inside fixed-height bars, as UIKit does in navigation and tab bars. */
@Composable
fun WithoutFontScaling(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalDensity provides LocalDensity.current.withFontScaleAtMost(1f), content = content)
}

private fun Density.withFontScaleAtMost(max: Float): Density =
    if (fontScale <= max) this else Density(density, max)
