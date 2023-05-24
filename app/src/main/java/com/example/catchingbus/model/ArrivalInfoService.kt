package com.example.catchingbus.model

import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Station
import com.example.catchingbus.model.json.ArrivalJsonApi
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object ArrivalInfoService {
    suspend fun search(station: Station, bus: Bus): ArrivalInfo {
        val jsons = ArrivalJsonApi.request("22", station.id, bus.id)
        val remainingTimes = jsons.map { it.arrtime.toDuration(DurationUnit.SECONDS) }
        return ArrivalInfo(station, bus, remainingTimes)
    }
}