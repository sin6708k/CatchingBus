package com.example.catchingbus.model.json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ArrivalJsonApiTest: StringSpec({

    "request correctly" {
        val jsons = ArrivalJsonApi.request("22", "DGB7021025800", "DGB3000719000")
        println(jsons.joinToString("\n", "\n"))
        jsons.isNotEmpty() shouldBe true
    }

    "request incorrectly" {
        val jsons = ArrivalJsonApi.request("22", "", "")
        println(jsons.joinToString("\n", "\n"))
        jsons.isEmpty() shouldBe true
    }
})