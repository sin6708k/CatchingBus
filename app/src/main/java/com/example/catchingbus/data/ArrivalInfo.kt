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
        Velocity.UNDETERMINED
    }
}