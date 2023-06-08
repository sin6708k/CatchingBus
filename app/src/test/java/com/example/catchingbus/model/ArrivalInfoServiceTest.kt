package com.example.catchingbus.model

import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Station
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ArrivalInfoServiceTest: StringSpec({

    "search" {
        val station = Station(
            "DGB7021025800",
            "경북대학교북문앞",
            35.89294,
            128.60996
        )
        val bus = Bus(
            "DGB3000719000",
            "719",
            0,
            "간선버스"
        )
        val arrivalInfo = ArrivalInfoService.search(station, bus)
        println(arrivalInfo)
        arrivalInfo.remainingTimes.isNotEmpty() shouldBe true
    }
})