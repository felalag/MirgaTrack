package com.migratrack.app.ui

import kotlin.time.Duration

/** "7 h 20 min", or "45 min" under an hour. */
fun formatHoursMinutes(duration: Duration): String {
    val hours = duration.inWholeHours
    val minutes = duration.inWholeMinutes % 60
    return if (hours == 0L) "$minutes min" else "$hours h $minutes min"
}
