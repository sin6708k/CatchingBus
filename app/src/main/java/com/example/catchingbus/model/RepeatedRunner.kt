package com.example.catchingbus.model

import com.example.catchingbus.data.Schedule
import kotlinx.coroutines.delay

abstract class RepeatedRunner(
    private val activeSchedules: List<Schedule>
) {
    suspend fun start() {
        while (true) {
            val repeatCommand = run() ?: break
            delay(repeatCommand.delay)
        }
    }

    abstract suspend fun run(): RepeatCommand?
}