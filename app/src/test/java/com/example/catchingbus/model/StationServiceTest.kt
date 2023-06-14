package com.example.catchingbus.model

import com.example.catchingbus.model.json.StationJsonApi
import com.example.catchingbus.viewmodel.GlobalInitializer
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class StationServiceTest: StringSpec({

    beforeSpec {
        StationJsonApi.initialize(GlobalInitializer.SERVICE_KEY)
    }

    "search" {
        val stations = StationService.search("경북대학교북문")
        println(stations.joinToString("\n", "\n"))
        stations.isNotEmpty() shouldBe true
    }
})