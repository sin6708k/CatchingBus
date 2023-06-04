package com.example.catchingbus

import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Station
import com.example.catchingbus.model.ArrivalInfoService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class ArrivalInfoServiceTest {
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
    fun search_isNotEmpty() = runTest {
        val arrivalInfo = ArrivalInfoService.search(tempStation, tempBus)
        println("\n" + arrivalInfo)

        assertTrue(arrivalInfo.remainingTimes.isNotEmpty())
    }
}