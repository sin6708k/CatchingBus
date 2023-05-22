package com.example.catchingbus

import com.example.catchingbus.model.json.ArrivalJsonApi
import com.example.catchingbus.model.json.BusByStationJsonApi
import com.example.catchingbus.model.json.BusJsonApi
import com.example.catchingbus.model.json.StationJsonApi
import org.junit.Test
import org.junit.Assert.*

class JsonApiTest {
    @Test
    fun requestStationJsons_isNotEmpty() {
        val jsons = StationJsonApi.request("22", "경북대학교북문")
        assertTrue(jsons.isNotEmpty())
        jsons.forEach { println(it) }
    }
    @Test
    fun requestBusByStationJsons_isNotEmpty() {
        val jsons = BusByStationJsonApi.request("22", "DGB7021025800")
        assertTrue(jsons.isNotEmpty())
        jsons.forEach { println(it) }
    }
    @Test
    fun requestBusJsons_isNotEmpty() {
        val jsons = BusJsonApi.request("22", "DGB3000719000")
        assertTrue(jsons.isNotEmpty())
        jsons.forEach { println(it) }
    }
    @Test
    fun requestArrivalJsons_isNotEmpty() {
        val jsons = ArrivalJsonApi.request("22", "DGB7021025800", "DGB3000719000")
        assertTrue(jsons.isNotEmpty())
        jsons.forEach { println(it) }
    }
}