package com.example.catchingbus.model

import com.example.catchingbus.model.json.CityJsonApi
import com.example.catchingbus.viewmodel.GlobalInitializer
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CityServiceTest: StringSpec({

    beforeSpec {
        CityJsonApi.initialize(GlobalInitializer.SERVICE_KEY)
    }

    "search" {
        val cities = CityService.search()
        println(cities.joinToString("\n", "\n"))
        cities.isNotEmpty() shouldBe true
    }
})