package com.migratrack.domain.analytics

import com.migratrack.domain.model.Episode

/** One trigger and the number of episodes that noted it. [name] is the stored copy of the newest episode. */
data class TriggerCount(val triggerId: String, val name: String, val episodes: Int)

/**
 * How often each trigger was noted in a set of episodes. It counts co-occurrence only:
 * a trigger noted often is not proof that it causes the attacks.
 */
data class TriggerFrequency(
    val episodes: Int,
    val withoutTriggers: Int,
    /** Most often noted first. The UI orders ties by the shown name, which depends on the language. */
    val counts: List<TriggerCount>,
) {
    companion object {
        fun of(episodes: List<Episode>): TriggerFrequency {
            // The newest episode gives the name, in case an older copy has an outdated one.
            val names = episodes.sortedBy { it.startedAt }.flatMap { it.triggers }.associate { it.triggerId to it.name }
            val counts = episodes
                .flatMap { episode -> episode.triggers.map { it.triggerId }.distinct() }
                .groupingBy { it }
                .eachCount()
                .map { (id, count) -> TriggerCount(id, names.getValue(id), count) }
                .sortedWith(compareByDescending<TriggerCount> { it.episodes }.thenBy { it.triggerId })
            return TriggerFrequency(episodes.size, episodes.count { it.triggers.isEmpty() }, counts)
        }
    }
}
