package com.migratrack.domain.analytics

import com.migratrack.domain.model.Episode

/** One trigger and the number of episodes that noted it. */
data class TriggerCount(val triggerId: String, val name: String, val episodes: Int)

/**
 * How often each trigger was noted in a set of episodes. It counts co-occurrence only:
 * a trigger noted often is not proof that it causes the attacks.
 */
data class TriggerFrequency(
    val episodes: Int,
    val withoutTriggers: Int,
    /** Most often noted first; ties in name order. */
    val counts: List<TriggerCount>,
) {
    /** "1 of 3 episodes has no triggers noted": the noun follows the total, the verb the first number. */
    val withoutTriggersText: String
        get() = "$withoutTriggers of $episodes ${if (episodes == 1) "episode" else "episodes"} " +
            "${if (withoutTriggers == 1) "has" else "have"} no triggers noted"

    companion object {
        fun of(episodes: List<Episode>): TriggerFrequency {
            // The newest episode gives the name, in case an older copy has an outdated one.
            val names = episodes.sortedBy { it.startedAt }.flatMap { it.triggers }.associate { it.triggerId to it.name }
            val counts = episodes
                .flatMap { episode -> episode.triggers.map { it.triggerId }.distinct() }
                .groupingBy { it }
                .eachCount()
                .map { (id, count) -> TriggerCount(id, names.getValue(id), count) }
                .sortedWith(compareByDescending<TriggerCount> { it.episodes }.thenBy { it.name })
            return TriggerFrequency(episodes.size, episodes.count { it.triggers.isEmpty() }, counts)
        }
    }
}
