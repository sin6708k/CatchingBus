package com.example.catchingbus.data

import kotlinx.datetime.Clock
import kotlin.time.Duration

data class ArrivalInfo(
    val station: Station,
    val bus: Bus,
    val remainingTimes: List<Duration>,
) {
    val creationTime = Clock.System.now()

    val nowRemainingTimes: List<Duration> get() {
        return remainingTimes.map {
            it - (Clock.System.now() - creationTime)
        }
    }

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