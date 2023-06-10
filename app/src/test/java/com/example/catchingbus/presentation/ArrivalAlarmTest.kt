package com.example.catchingbus.presentation

import com.example.catchingbus.data.AlarmMessage
import com.example.catchingbus.model.ScheduleRepo
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ArrivalAlarmTest: StringSpec({

    beforeTest {
        ScheduleRepo.load()
        println(ScheduleRepo.data.value.joinToString("\n * ", "Schedule\n * "))
    }

    "start" {
        val result = mutableListOf<AlarmMessage>()

        CoroutineScope(Dispatchers.Default).launch {
            ArrivalAlarm.alarmMessage.collect {
                println(it)
                result.add(it)
            }
        }

        ArrivalAlarm.start(1.toDuration(DurationUnit.SECONDS))

        delay(3500.toDuration(DurationUnit.MILLISECONDS))
        result.size shouldBe 3
    }
})