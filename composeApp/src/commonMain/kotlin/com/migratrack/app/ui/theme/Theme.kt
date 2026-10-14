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
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.migratrack.app.generated.resources.Res
import com.migratrack.app.generated.resources.plus_jakarta_sans_bold
import com.migratrack.app.generated.resources.plus_jakarta_sans_extra_bold
import com.migratrack.app.generated.resources.plus_jakarta_sans_medium
import com.migratrack.app.generated.resources.plus_jakarta_sans_regular
import com.migratrack.app.generated.resources.plus_jakarta_sans_semi_bold
import org.jetbrains.compose.resources.Font

/** Type scale of the specification, set in Plus Jakarta Sans. */
@Immutable
class MtTypography(family: FontFamily) {
    private val base = TextStyle(fontFamily = family, color = MtColors.TextPrimary)
    private val numbers = base.copy(fontFeatureSettings = "tnum")

    /** Big statistics: attack counts, sleep hours. */
    val hero = numbers.copy(fontSize = 40.sp, lineHeight = 48.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = (-0.02).em)
    val display = numbers.copy(fontSize = 34.sp, lineHeight = 40.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.02).em)
    /** Onboarding page titles. */
    val largeTitle = base.copy(fontSize = 28.sp, lineHeight = 34.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.01).em)
    val title1 = base.copy(fontSize = 24.sp, lineHeight = 28.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.01).em)
    val title2 = base.copy(fontSize = 20.sp, lineHeight = 24.sp, fontWeight = FontWeight.SemiBold)
    val headline = base.copy(fontSize = 17.sp, lineHeight = 22.sp, fontWeight = FontWeight.SemiBold)
    val body = base.copy(fontSize = 15.sp, lineHeight = 20.sp, fontWeight = FontWeight.Normal)
    val bodyStrong = body.copy(fontWeight = FontWeight.SemiBold)
    val subhead = base.copy(fontSize = 13.sp, lineHeight = 18.sp, fontWeight = FontWeight.Medium)
    val caption = base.copy(fontSize = 11.sp, lineHeight = 14.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.06.em)
    val tabLabel = base.copy(fontSize = 11.sp, lineHeight = 14.sp, fontWeight = FontWeight.Medium)
    val number = numbers
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
    val typography = remember(family) { MtTypography(family) }
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
