package com.migratrack.domain.model

import kotlin.jvm.JvmInline

/** Pain on the 1–10 scale that the logging and onboarding screens use. */
@JvmInline
value class PainIntensity(val value: Int) {
    init {
        require(value in RANGE) { "Pain intensity must be in $RANGE, was $value" }
    }

    override fun toString(): String = "$value/10"

    companion object {
        val RANGE: IntRange = 1..10
        val Default: PainIntensity = PainIntensity(7)
    }
}
