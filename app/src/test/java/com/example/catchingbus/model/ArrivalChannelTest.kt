package com.example.catchingbus.model

import com.example.catchingbus.data.ArrivalChannelMessage
import com.example.catchingbus.model.json.ArrivalJsonApi
import com.example.catchingbus.viewmodel.GlobalInitializer
import io.kotest.assertions.timing.eventually
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.io.path.Path
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ArrivalChannelTest: StringSpec({

    beforeSpec {
        ArrivalJsonApi.initialize(GlobalInitializer.SERVICE_KEY)
    }

    beforeTest {
        ScheduleRepo.load(Path("schedules.txt"))
        println(ScheduleRepo.data.value.joinToString("\n ", "Schedule\n "))
    }

    "start" {
        val result = mutableListOf<ArrivalChannelMessage>()

        CoroutineScope(Dispatchers.Default).launch {
            ArrivalChannel.message.collect {
                println(it)
                result.add(it)
            }
        }
        ArrivalChannel.start(
            period = 1.toDuration(DurationUnit.SECONDS),
            passCountWhenHit = 2
        )
        eventually(500.toDuration(DurationUnit.MILLISECONDS)) {
            result.size shouldBe 1
        }
        eventually(3500.toDuration(DurationUnit.SECONDS)) {
            result.size shouldBe 2
        }
    }
})