package com.example.catchingbus.model.json

import com.example.catchingbus.viewmodel.GlobalInitializer
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ArrivalJsonApiTest: StringSpec({

    beforeSpec {
        ArrivalJsonApi.initialize(GlobalInitializer.SERVICE_KEY)
    }

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