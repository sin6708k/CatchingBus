package com.example.catchingbus.model.json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CityJsonApiTest: StringSpec({

    "request correctly" {
        val jsons = CityJsonApi.request()
        println(jsons.joinToString("\n", "\n"))
        jsons.isNotEmpty() shouldBe true
    }
})