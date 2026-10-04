package com.migratrack.app

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

/** Swift entry point for the phone's WatchConnectivity session. Swift only moves the JSON strings. */
object PhoneWatchBridge {
    private val scope = MainScope()

    /** Calls [onContext] on the main thread now and after every change to the episodes or the preset. */
    fun observeContext(onContext: (String) -> Unit) {
        scope.launch { appGraph.phoneSync.contexts().collect { onContext(it) } }
    }

    /** Applies a batch from the watch and returns the context that confirms it. */
    @Throws(IllegalArgumentException::class, CancellationException::class)
    suspend fun receive(batch: String): String {
        appGraph.phoneSync.receive(batch)
        return appGraph.phoneSync.context()
    }
}
