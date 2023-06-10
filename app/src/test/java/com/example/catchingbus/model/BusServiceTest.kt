package com.example.catchingbus.model

import com.example.catchingbus.data.Station
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class BusServiceTest: StringSpec({

    "search" {
        val station = Station(
            id = "DGB7021025800",
            name = "경북대학교북문앞",
            latitude = 35.89294,
            longitude = 128.60996
        )
        val buses = BusService.search(station)
        println(buses.joinToString("\n", "\n"))
        buses.isNotEmpty() shouldBe true
    }
})