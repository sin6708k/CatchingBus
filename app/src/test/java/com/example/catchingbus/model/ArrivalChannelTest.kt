package com.example.catchingbus.model

import com.example.catchingbus.data.ArrivalChannelMessage
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ArrivalChannelTest: StringSpec({

    beforeTest {
        ScheduleRepo.load("")
        println(ScheduleRepo.data.value.joinToString("\n * ", "Schedule\n * "))
    }

    "start" {
        val result = mutableListOf<ArrivalChannelMessage>()

        CoroutineScope(Dispatchers.Default).launch {
            ArrivalChannel.message.collect {
                println(it)
                result.add(it)
            }
        }

        ArrivalChannel.start(1.toDuration(DurationUnit.SECONDS))

        delay(3500.toDuration(DurationUnit.MILLISECONDS))
        result.size shouldBe 3
    }
})