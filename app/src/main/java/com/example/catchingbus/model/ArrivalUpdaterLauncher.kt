package com.example.catchingbus.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class ArrivalUpdaterLauncher(coroutineScope: CoroutineScope) {
    var updaters by Delegates.observable(listOf<ArrivalUpdater>()) { _, _, new ->
        jobs.forEach { it.cancel() }
        jobs = new.map {
            coroutineScope.launch { it.start() }
        }
    }
    private var jobs = listOf<Job>()
}