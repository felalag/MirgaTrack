package com.migratrack.app.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/** Colors of the MigraTrack design specification ("Luminescent Clinical Dark"). */
object MtColors {
    val Pink = Color(0xFFFF2D55)
    val Cyan = Color(0xFF00F0FF)
    val Green = Color(0xFF30D158)
    val Orange = Color(0xFFFF9F0A)
    val Amber = Color(0xFFFF9500)
    val Purple = Color(0xFFBF5AF2)

    /** The light pink of the brand mark, the calendar icons, and the attack days of the calendar. */
    val PinkSoft = Color(0xFFFF8093)

    /** The lime of the online dot on the profile avatar. */
    val Lime = Color(0xFF8EDB00)

    val Canvas = Color(0xFF131315)
    val CanvasDeep = Color(0xFF0E0E10)
    val Card = Color(0xFF1B1B1E)
    val CardRaised = Color(0xFF1F1F21)
    val Control = Color(0xFF2A2A2C)
    val ControlHigh = Color(0xFF353437)

    val TextPrimary = Color(0xFFFFFFFF)
    val TextBody = Color(0xFFE4E2E4)
    val TextSecondary = Color(0xFF8E8E93)
    val TextTertiary = Color(0xFF48484A)
    /** The pink-tinted silver of the uppercase captions in every screen. */
    val Caption = Color(0xFFE6BCBD)
    val BrandLabel = Color(0xFFF8A5AB)

    val BorderSubtle = Color(0x14FFFFFF)
    val SwitchOff = Color(0xFF39393D)
}

object MtSpacing {
    val xs = 4.dp
    val sm = 8.dp
    val md = 16.dp
    val lg = 24.dp

    /** Side margin of every screen. */
    val screen = 20.dp
    val minTouch = 44.dp
}

object MtRadius {
    val sm = 8.dp
    val md = 12.dp
    val lg = 16.dp
    val xl = 24.dp
}
