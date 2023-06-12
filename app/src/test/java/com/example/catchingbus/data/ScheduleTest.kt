package com.example.catchingbus.data

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import kotlinx.datetime.toKotlinLocalTime
import java.time.LocalTime
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ScheduleTest: StringSpec({

    lateinit var schedule1: Schedule
    lateinit var schedule2: Schedule

    beforeTest {
        schedule1 = Schedule(
            favorite = Favorite(
                station = Station(
                    id = "DGB7021025800",
                    name = "경북대학교북문앞",
                    latitude = 35.89294,
                    longitude = 128.60996
                ),
                bus = Bus(
                    id = "DGB3000719000",
                    name = "719",
                    intervalTime = 0.toDuration(DurationUnit.MINUTES),
                    type = "간선버스"
                )
            ),
            startTime = LocalTime.of(12, 0, 0).toKotlinLocalTime(),
            endTime = LocalTime.of(18, 0, 0).toKotlinLocalTime(),
        )
        schedule2 = Schedule(
            Favorite(
                station = Station(
                    id = "DGB7021025800",
                    name = "",
                    latitude = 0.0,
                    longitude = 0.0
                ),
                bus = Bus(
                    id = "DGB3000719000",
                    name = "0",
                    intervalTime = 0.toDuration(DurationUnit.MINUTES),
                    type = ""
                )
            ),
            startTime = LocalTime.of(12, 0, 0).toKotlinLocalTime(),
            endTime = LocalTime.of(18, 0, 0).toKotlinLocalTime(),
        )
    }

    "equals" {
        schedule1 shouldBeEqual schedule2
    }
})