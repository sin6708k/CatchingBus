package com.example.catchingbus.model

import com.example.catchingbus.model.json.StationJsonApi

data class Station(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        suspend fun search(word: String): List<Station> {
            val jsons = StationJsonApi.request("22", word)
            return jsons.map {
                Station(it.nodeid, it.nodenm, it.gpslati, it.gpslong)
            }
        }
    }
}
