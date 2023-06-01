package com.example.catchingbus

import org.junit.Test
import org.junit.Assert.*

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
    fun searchStations_isNotEmpty() {
        val stations = Station.search("경북대학교북문")
        assertTrue(stations.isNotEmpty())
        stations.forEach { println(it) }
    }
    @Test
    fun searchBuses_isNotEmpty() {
        val buses = Bus.search(tempStation)
        assertTrue(buses.isNotEmpty())
        buses.forEach { println(it) }
    }
    @Test
    fun searchArrivalInfo_isNotEmpty() {
        val arrivalInfo = ArrivalInfo.search(tempStation, tempBus)
        assertTrue(arrivalInfo.remainingTimes.isNotEmpty())
        print(arrivalInfo)
    }
}