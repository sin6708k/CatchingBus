package com.example.catchingbus.model

import kotlinx.coroutines.delay

abstract class Runnable(
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