package com.example.catchingbus.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class StationServiceTest: StringSpec({

    "search" {
        val stations = StationService.search("경북대학교북문")
        println(stations.joinToString("\n", "\n"))
        stations.isNotEmpty() shouldBe true
    }
})