package com.example.catchingbus.model

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CityServiceTest: StringSpec({

    "search" {
        val cities = CityService.search()
        println(cities.joinToString("\n", "\n"))
        cities.isNotEmpty() shouldBe true
    }
})