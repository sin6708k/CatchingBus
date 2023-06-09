package com.example.catchingbus.model

import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Station
import com.example.catchingbus.model.json.ArrivalJsonApi
import com.example.catchingbus.viewmodel.GlobalInitializer
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ArrivalInfoServiceTest: StringSpec({

    lateinit var station: Station
    lateinit var bus: Bus

    beforeSpec {
        ArrivalJsonApi.initialize(GlobalInitializer.SERVICE_KEY)
    }

    beforeTest {
        station = Station(
            id = "DGB7021025800",
            name = "경북대학교북문앞",
            latitude = 35.89294,
            longitude = 128.60996
        )
        bus = Bus(
            id = "DGB3000719000",
            name = "719",
            intervalTime = 0.toDuration(DurationUnit.MINUTES),
            type = "간선버스"
        )
    }

    "search()가 잘 되는가?" {
        val arrivalInfo = ArrivalInfoService.search(station, bus)
        println(arrivalInfo)
        arrivalInfo.remainingTimes.isNotEmpty() shouldBe true
    }
})