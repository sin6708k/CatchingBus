package com.example.catchingbus.model.json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class StationJsonApiTest: StringSpec({

    "request correctly" {
        val jsons = StationJsonApi.request("22", "경북대학교북문")
        println(jsons.joinToString("\n", "\n"))
        jsons.isNotEmpty() shouldBe true
    }
})