package com.example.catchingbus

import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Station
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
    fun searchStations_isNotEmpty() = runTest {
        val stations = Station.search("경북대학교북문")
        assertTrue(stations.isNotEmpty())
        stations.forEach { println(it) }
    }

    @Test
    fun searchBuses_isNotEmpty() = runTest {
        val buses = Bus.search(tempStation)
        assertTrue(buses.isNotEmpty())
        buses.forEach { println(it) }
    }

    @Test
    fun searchArrivalInfo_isNotEmpty() = runTest {
        val arrivalInfo = ArrivalInfo.search(tempStation, tempBus)
        assertTrue(arrivalInfo.remainingTimes.isNotEmpty())
        print(arrivalInfo)
    }
}