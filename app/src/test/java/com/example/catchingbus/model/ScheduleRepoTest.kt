package com.example.catchingbus.model

import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Schedule
import com.example.catchingbus.data.Station
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalTime
import java.time.LocalTime
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ScheduleRepoTest: StringSpec({

    lateinit var schedule: Schedule

    beforeTest {
        ScheduleRepo.load("")

        CoroutineScope(Dispatchers.Default).launch {
            ScheduleRepo.data.collectLatest {
                println(it.joinToString("\n * ", "collectLatest\n * "))
            }
        }

        schedule = Schedule(
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
                )),
            startTime = LocalTime.of(5, 30, 0).toKotlinLocalTime(),
            endTime = LocalTime.of(23, 30, 0).toKotlinLocalTime()
        )
    }

    "clear" {
        ScheduleRepo.clear()
        println(ScheduleRepo.data.value.joinToString("\n * ", "clear\n * "))
        ScheduleRepo.data.value.isEmpty() shouldBe true
        delay(1.toDuration(DurationUnit.SECONDS))
    }

    "add" {
        ScheduleRepo.add(schedule)
        println(ScheduleRepo.data.value.joinToString("\n * ", "add\n * "))
        ScheduleRepo.data.value.isNotEmpty() shouldBe true
        delay(1.toDuration(DurationUnit.SECONDS))
    }
})