package com.migratrack.app.platform

import com.migratrack.domain.model.SleepSummary
import com.migratrack.domain.platform.SleepAccess
import com.migratrack.domain.platform.SleepDataSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.todayIn
import platform.Foundation.NSDate
import platform.Foundation.NSSortDescriptor
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.timeIntervalSince1970
import platform.HealthKit.HKAuthorizationRequestStatusShouldRequest
import platform.HealthKit.HKAuthorizationRequestStatusUnnecessary
import platform.HealthKit.HKCategorySample
import platform.HealthKit.HKCategoryTypeIdentifierSleepAnalysis
import platform.HealthKit.HKHealthStore
import platform.HealthKit.HKObjectQueryNoLimit
import platform.HealthKit.HKObjectType
import platform.HealthKit.HKQuery
import platform.HealthKit.HKQueryOptionNone
import platform.HealthKit.HKSampleQuery
import platform.HealthKit.HKSampleSortIdentifierStartDate
import platform.HealthKit.predicateForSamplesWithStartDate
import kotlin.coroutines.resume
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.Instant

/** Reads last night's sleep stages from HealthKit. MigraTrack only reads; it never writes to Health. */
class HealthKitSleepDataSource(private val clock: Clock = Clock.System) : SleepDataSource {
    private val store = HKHealthStore()
    private val sleepType = requireNotNull(HKObjectType.categoryTypeForIdentifier(HKCategoryTypeIdentifierSleepAnalysis))

    override suspend fun access(): SleepAccess {
        if (!HKHealthStore.isHealthDataAvailable()) return SleepAccess.Unavailable
        return suspendCancellableCoroutine { continuation ->
            store.getRequestStatusForAuthorizationToShareTypes(emptySet<Any>(), setOf(sleepType)) { status, _ ->
                continuation.resume(
                    when (status) {
                        HKAuthorizationRequestStatusShouldRequest -> SleepAccess.NotRequested
                        HKAuthorizationRequestStatusUnnecessary -> SleepAccess.Requested
                        else -> SleepAccess.Unavailable
                    },
                )
            }
        }
    }

    override suspend fun requestAccess(): Boolean {
        if (!HKHealthStore.isHealthDataAvailable()) return false
        return suspendCancellableCoroutine { continuation ->
            store.requestAuthorizationToShareTypes(null, setOf(sleepType)) { success, _ -> continuation.resume(success) }
        }
    }

    override suspend fun lastNight(): SleepSummary? {
        if (!HKHealthStore.isHealthDataAvailable()) return null
        val zone = TimeZone.currentSystemDefault()
        val today = clock.todayIn(zone)
        // "Last night" is the window from 18:00 yesterday to 14:00 today, which covers late sleepers and naps after dawn.
        val from = today.minus(DatePeriod(days = 1)).atTime(LocalTime(18, 0)).toInstant(zone)
        val until = minOf(today.atTime(LocalTime(14, 0)).toInstant(zone), clock.now())
        val samples = querySamples(from, until)
        if (samples.isEmpty()) return null

        // iPhone, Apple Watch and third-party apps record the same night; summing them would double count it.
        return samples
            .groupBy { it.sourceRevision.source.bundleIdentifier }
            .values
            .map(::summarize)
            .maxByOrNull { it.asleep }
            ?.takeIf { it.asleep > Duration.ZERO }
    }

    private suspend fun querySamples(from: Instant, until: Instant): List<HKCategorySample> =
        suspendCancellableCoroutine { continuation ->
            val predicate = HKQuery.predicateForSamplesWithStartDate(from.toNSDate(), until.toNSDate(), HKQueryOptionNone)
            val query = HKSampleQuery(
                sampleType = sleepType,
                predicate = predicate,
                limit = HKObjectQueryNoLimit,
                sortDescriptors = listOf(NSSortDescriptor(HKSampleSortIdentifierStartDate, ascending = true)),
            ) { _, results, _ ->
                continuation.resume(results.orEmpty().filterIsInstance<HKCategorySample>())
            }
            continuation.invokeOnCancellation { store.stopQuery(query) }
            store.executeQuery(query)
        }

    private fun summarize(samples: List<HKCategorySample>): SleepSummary {
        var deep = Duration.ZERO
        var core = Duration.ZERO
        var rem = Duration.ZERO
        for (sample in samples) {
            val length = (sample.endDate.timeIntervalSince1970 - sample.startDate.timeIntervalSince1970).seconds
            // HKCategoryValueSleepAnalysis: 1 asleep without stage data, 3 core, 4 deep, 5 REM.
            when (sample.value) {
                1L, 3L -> core += length
                4L -> deep += length
                5L -> rem += length
            }
        }
        return SleepSummary(deep = deep, core = core, rem = rem)
    }
}

private fun Instant.toNSDate(): NSDate = NSDate.dateWithTimeIntervalSince1970(toEpochMilliseconds() / 1000.0)
