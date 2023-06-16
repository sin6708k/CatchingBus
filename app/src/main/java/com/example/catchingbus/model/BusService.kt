package com.example.catchingbus.model

import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.City
import com.example.catchingbus.data.Station
import com.example.catchingbus.model.json.BusByStationJsonApi
import com.example.catchingbus.model.json.BusJsonApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object BusService {
    suspend fun search(
        station: Station,
        city: City = City.default
    ): List<Bus> {
        val busByStationJsons = BusByStationJsonApi.request(city.code.toString(), station.id)
        val busJsons = coroutineScope {
            busByStationJsons.map {
                async { BusJsonApi.request(city.code.toString(), it.routeid).first() }
            }.awaitAll()
        }
        return busJsons.map { Bus(it.routeid, it.routeno, it.intervaltime.toDuration(DurationUnit.MINUTES), it.routetp) }
    }
}