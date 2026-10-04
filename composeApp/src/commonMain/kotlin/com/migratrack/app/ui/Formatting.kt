package com.migratrack.app.ui

import kotlin.time.Duration

/** "1h 50m", or "45m" under an hour. */
fun formatHoursMinutes(duration: Duration): String {
    val hours = duration.inWholeHours
    val minutes = duration.inWholeMinutes % 60
    return if (hours == 0L) "${minutes}m" else "${hours}h ${minutes}m"
}
