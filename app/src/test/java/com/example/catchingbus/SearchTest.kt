package com.example.catchingbus

import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Station
import com.example.catchingbus.model.ArrivalInfoService
import com.example.catchingbus.model.BusService
import com.example.catchingbus.model.CityService
import com.example.catchingbus.model.StationService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class SearchTest {
    private val tempStation = Station(
        "DGB7021025800",
        "경북대학교북문앞",
        35.89294,
        128.60996
    )
    private val tempBus = Bus(
        "DGB3000719000",
        "719",
        0,
        "간선버스"
    )

    @Test
    fun searchCities_isNotEmpty() = runTest {
        val cities = CityService.search()
        assertTrue(cities.isNotEmpty())

        val message = cities.joinToString("\n")
        println(message)
    }

    @Test
    fun searchStations_isNotEmpty() = runTest {
        val stations = StationService.search("경북대학교북문")
        assertTrue(stations.isNotEmpty())

        val message = stations.joinToString("\n")
        println(message)
    }

    @Test
    fun searchBuses_isNotEmpty() = runTest {
        val buses = BusService.search(tempStation)
        assertTrue(buses.isNotEmpty())

        val message = buses.joinToString("\n")
        println(message)
    }

    @Test
    fun searchArrivalInfo_isNotEmpty() = runTest {
        val arrivalInfo = ArrivalInfoService.search(tempStation, tempBus)
        assertTrue(arrivalInfo.remainingTimes.isNotEmpty())

        println(arrivalInfo)
    }
}