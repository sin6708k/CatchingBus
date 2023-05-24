package com.example.catchingbus.data

import com.example.catchingbus.model.json.ArrivalJsonApi
import java.time.LocalDateTime
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

data class ArrivalInfo(
    val station: Station,
    val bus: Bus,
    val remainingTimes: List<Duration>,
) {
    val creationTime = LocalDateTime.now()

    val velocity: Velocity by lazy {
        Velocity.UNDETERMINED
    }

    companion object {
        suspend fun search(station: Station, bus: Bus): ArrivalInfo {
            val jsons = ArrivalJsonApi.request("22", station.id, bus.id)
            val remainingTimes = jsons.map { it.arrtime.toDuration(DurationUnit.SECONDS) }
            return ArrivalInfo(station, bus, remainingTimes)
        }
    }
}