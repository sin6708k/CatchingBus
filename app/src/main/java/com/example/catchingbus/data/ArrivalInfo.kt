package com.example.catchingbus.data

import kotlinx.datetime.Clock
import kotlin.time.Duration

data class ArrivalInfo(
    val station: Station,
    val bus: Bus,
    val remainingTimes: List<Duration>,
) {
    val creationTime = Clock.System.now()

    val nowRemainingTime: List<Duration> get() {
        return remainingTimes.map {
            it - (Clock.System.now() - creationTime)
        }
    }

    /*
    val nowRemainingTime: List<String> get() {
        return remainingTimes.map { duration ->
            val minutes = duration.inWholeMinutes
            val seconds = duration.inWholeSeconds % 60
            String.format("%02d:%02d", minutes, seconds)
        }
    }

     */
    val velocity: Velocity by lazy {
        if (remainingTimes.size >= 2) {
            if (remainingTimes[1] - remainingTimes[0] < bus.intervalTime) {
                Velocity.FAST
            } else {
                Velocity.SLOW
            }
        } else {
            Velocity.UNDETERMINED
        }
    }
}