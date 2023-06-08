package com.example.catchingbus.model.json

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class BusJsonApiTest: StringSpec({

    "request correctly" {
        val jsons = BusJsonApi.request("22", "DGB3000719000")
        println(jsons.joinToString("\n", "\n"))
        jsons.isNotEmpty() shouldBe true
    }

    "request incorrectly" {
        val jsons = BusJsonApi.request("22", "")
        println(jsons.joinToString("\n", "\n"))
        jsons.isEmpty() shouldBe true
    }
})