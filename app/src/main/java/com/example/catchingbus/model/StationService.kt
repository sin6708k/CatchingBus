package com.example.catchingbus.model

import com.example.catchingbus.data.Station
import com.example.catchingbus.model.json.StationJsonApi

object StationService {
    suspend fun search(word: String): List<Station> {
        val jsons = StationJsonApi.request("22", word)
        return jsons.map {
            Station(it.nodeid, it.nodenm, it.gpslati, it.gpslong)
        }
    }
}