package com.example.catchingbus

import com.example.catchingbus.data.Station
import com.example.catchingbus.model.BusService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BusServiceTest {
    private val tempStation = Station(
        "DGB7021025800",
        "경북대학교북문앞",
        35.89294,
        128.60996
    )

    @Test
    fun search_isNotEmpty() = runTest {
        val buses = BusService.search(tempStation)

        val message = buses.joinToString("\n", "\n")
        println(message)

        assertTrue(buses.isNotEmpty())
    }
}