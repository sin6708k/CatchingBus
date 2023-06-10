package com.example.catchingbus.data

import java.time.LocalDateTime
import kotlin.time.Duration

data class ArrivalInfo(
    val station: Station,
    val bus: Bus,
    val remainingTimes: List<Duration>,
) {
    val creationTime = LocalDateTime.now()

    val velocity: Velocity by lazy {
        if (remainingTimes.size >= 2) {
            val firstTime = remainingTimes[0]
            val secondTime = remainingTimes[1]

            if (secondTime - firstTime < bus.intervalTime) {
                Velocity.FAST
            } else {
                Velocity.SLOW
            }
        } else {
            Velocity.UNDETERMINED
        }
    }
}