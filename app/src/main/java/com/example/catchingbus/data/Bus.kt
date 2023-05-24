package com.example.catchingbus.data

import com.example.catchingbus.model.json.BusByStationJsonApi
import com.example.catchingbus.model.json.BusJsonApi

data class Bus(
    val id: String,
    val name: String,
    val intervalTime: Int,
    val type: String
) {
    companion object {
        suspend fun search(station: Station): List<Bus> {
            val busByStationJsons = BusByStationJsonApi.request("22", station.id)
            val busJsons = busByStationJsons.map {
                BusJsonApi.request("22", it.routeid).first()
            }
            return busJsons.map {
                Bus(it.routeid, it.routeno, it.intervaltime, it.routetp)
            }
        }
    }
}