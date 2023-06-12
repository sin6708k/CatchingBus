package com.example.catchingbus.model.json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class BusByStationJsonApiTest: StringSpec({

    "request correctly" {
        val jsons = BusByStationJsonApi.request("22", "DGB7021025800")
        println(jsons.joinToString("\n", "\n"))
        jsons.isNotEmpty() shouldBe true
    }

    "request incorrectly" {
        val jsons = BusByStationJsonApi.request("22", "-")
        println(jsons.joinToString("\n", "\n"))
        jsons.isEmpty() shouldBe true
    }
})