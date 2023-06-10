package com.example.catchingbus.model

import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.City
import com.example.catchingbus.data.Station
import com.example.catchingbus.model.json.ArrivalJsonApi
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object ArrivalInfoService {
    suspend fun search(
        station: Station,
        bus: Bus,
        city: City = City.default
    ): ArrivalInfo {
        val jsons = ArrivalJsonApi.request(city.code.toString(), station.id, bus.id)
        return ArrivalInfo(station, bus,
            remainingTimes = jsons.map { it.arrtime.toDuration(DurationUnit.SECONDS) }.sortedBy { it }
        )
    }
}