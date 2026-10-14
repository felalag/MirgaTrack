package com.migratrack.domain.model

/** A known or suspected migraine trigger from the triggers step of onboarding. */
data class Trigger(
    val id: String,
    val name: String,
    val description: String,
    val category: TriggerCategory,
    val isCustom: Boolean = false,
)

enum class TriggerCategory(val title: String) {
    WeatherEnvironment("Weather & Environment"),
    SleepPhysical("Sleep & Physical Routine"),
    StressEmotions("Stress & Emotions"),
    Hormonal("Hormonal"),
    DietConsumption("Diet & Consumption"),
    Custom("Custom"),
}

object TriggerCatalog {
    val defaults: List<Trigger> = listOf(
        Trigger("barometric-pressure", "Barometric Pressure Drop", "Weather fronts, altitude shifts", TriggerCategory.WeatherEnvironment),
        Trigger("bright-light", "Bright Lights & Sunlight", "Glare, strobe, photophobia", TriggerCategory.WeatherEnvironment),
        Trigger("heat-humidity", "Extreme Heat / Humidity", "Thermal fluctuations", TriggerCategory.WeatherEnvironment),
        Trigger("loud-noise", "Loud Noise", "Phonophobia, acoustic peaks", TriggerCategory.WeatherEnvironment),
        Trigger("poor-sleep", "Poor / Irregular Sleep", "Sleep debt, REM deficits", TriggerCategory.SleepPhysical),
        Trigger("dehydration", "Dehydration", "Electrolyte depletion", TriggerCategory.SleepPhysical),
        Trigger("skipped-meals", "Skipped Meals / Fasting", "Hypoglycemia threshold", TriggerCategory.SleepPhysical),
        Trigger("overexertion", "Physical Overexertion", "Exertional vascular strain", TriggerCategory.SleepPhysical),
        Trigger("emotional-stress", "Emotional Stress / Letdown", "Post-work decompression lag", TriggerCategory.StressEmotions),
        Trigger("screen-fatigue", "Screen Fatigue", "Blue-light overload, convergence", TriggerCategory.StressEmotions),
        Trigger("hormonal-cycle", "Hormonal Shifts / Cycle", "Monthly cycle variation", TriggerCategory.Hormonal),
        Trigger("caffeine", "Caffeine Withdrawal / Excess", "Adenosine receptor shifts", TriggerCategory.DietConsumption),
        Trigger("aged-cheese", "Aged Cheese / Tyramine", "Fermented amine sensitivity", TriggerCategory.DietConsumption),
        Trigger("alcohol", "Alcohol / Red Wine", "Tannins, histamine", TriggerCategory.DietConsumption),
        Trigger("sweeteners", "Artificial Sweeteners", "Aspartame, sucralose", TriggerCategory.DietConsumption),
    )
}
