package com.migratrack.domain.model

/**
 * A known or suspected migraine trigger from the triggers step of onboarding.
 * [name] is the text of a custom trigger. For a built-in trigger it is the English name that episodes store
 * as a copy; the UI shows the name of the [id] in the language of the app.
 */
data class Trigger(
    val id: String,
    val name: String,
    val category: TriggerCategory,
    val isCustom: Boolean = false,
)

enum class TriggerCategory { WeatherEnvironment, SleepPhysical, StressEmotions, Hormonal, DietConsumption, Custom }

object TriggerCatalog {
    val defaults: List<Trigger> = listOf(
        Trigger("barometric-pressure", "Barometric Pressure Drop", TriggerCategory.WeatherEnvironment),
        Trigger("bright-light", "Bright Lights & Sunlight", TriggerCategory.WeatherEnvironment),
        Trigger("heat-humidity", "Extreme Heat / Humidity", TriggerCategory.WeatherEnvironment),
        Trigger("loud-noise", "Loud Noise", TriggerCategory.WeatherEnvironment),
        Trigger("poor-sleep", "Poor / Irregular Sleep", TriggerCategory.SleepPhysical),
        Trigger("dehydration", "Dehydration", TriggerCategory.SleepPhysical),
        Trigger("skipped-meals", "Skipped Meals / Fasting", TriggerCategory.SleepPhysical),
        Trigger("overexertion", "Physical Overexertion", TriggerCategory.SleepPhysical),
        Trigger("emotional-stress", "Emotional Stress / Letdown", TriggerCategory.StressEmotions),
        Trigger("screen-fatigue", "Screen Fatigue", TriggerCategory.StressEmotions),
        Trigger("hormonal-cycle", "Hormonal Shifts / Cycle", TriggerCategory.Hormonal),
        Trigger("caffeine", "Caffeine Withdrawal / Excess", TriggerCategory.DietConsumption),
        Trigger("aged-cheese", "Aged Cheese / Tyramine", TriggerCategory.DietConsumption),
        Trigger("alcohol", "Alcohol / Red Wine", TriggerCategory.DietConsumption),
        Trigger("sweeteners", "Artificial Sweeteners", TriggerCategory.DietConsumption),
    )

    private val byId = defaults.associateBy { it.id }

    /** The built-in trigger with this ID, or null for a custom or removed one. */
    fun builtIn(id: String): Trigger? = byId[id]
}
