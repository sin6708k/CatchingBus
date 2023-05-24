package com.example.catchingbus

import com.example.catchingbus.model.json.ArrivalJsonApi
import com.example.catchingbus.model.json.BusByStationJsonApi
import com.example.catchingbus.model.json.BusJsonApi
import com.example.catchingbus.model.json.StationJsonApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class JsonApiTest {

    @Test
    fun requestStationJsons_isNotEmpty() = runTest {
        val jsons = StationJsonApi.request("22", "경북대학교북문")
        assertTrue(jsons.isNotEmpty())

        val message = jsons.joinToString("\n")
        println(message)
    }

    @Test
    fun requestBusByStationJsons_isNotEmpty() = runTest {
        val jsons = BusByStationJsonApi.request("22", "DGB7021025800")
        assertTrue(jsons.isNotEmpty())

        val message = jsons.joinToString("\n")
        println(message)
    }

    @Test
    fun requestBusJsons_isNotEmpty() = runTest {
        val jsons = BusJsonApi.request("22", "DGB3000719000")
        assertTrue(jsons.isNotEmpty())

        val message = jsons.joinToString("\n")
        println(message)
    }

    @Test
    fun requestArrivalJsons_isNotEmpty() = runTest {
        val jsons = ArrivalJsonApi.request("22", "DGB7021025800", "DGB3000719000")
        assertTrue(jsons.isNotEmpty())

        val message = jsons.joinToString("\n")
        println(message)
    }
}