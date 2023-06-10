package com.example.catchingbus.model

import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Schedule
import com.example.catchingbus.data.Station
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.toKotlinLocalTime
import java.time.LocalTime
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ScheduleRepoTest: StringSpec({

    beforeTest {
        ScheduleRepo.load()
    }

    "clear" {
        ScheduleRepo.clear()
        ScheduleRepo.data.value.isEmpty() shouldBe true
    }

    "add" {
        val station = Station(
            id = "DGB7021025800",
            name = "경북대학교북문앞",
            latitude = 35.89294,
            longitude = 128.60996
        )
        val bus = Bus(
            id = "DGB3000719000",
            name = "719",
            intervalTime = 0.toDuration(DurationUnit.MINUTES),
            type = "간선버스"
        )
        val schedule = Schedule(
            favorite = Favorite(station, bus),
            startTime = LocalTime.of(5, 30, 0).toKotlinLocalTime(),
            endTime = LocalTime.of(23, 30, 0).toKotlinLocalTime()
        )
        ScheduleRepo.add(schedule)
        ScheduleRepo.data.value.isNotEmpty() shouldBe true
    }
})