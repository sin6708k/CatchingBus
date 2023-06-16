package com.example.catchingbus.model.json

import com.example.catchingbus.viewmodel.GlobalInitializer
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ArrivalJsonApiTest: StringSpec({

    beforeSpec {
        ArrivalJsonApi.initialize(GlobalInitializer.SERVICE_KEY)
    }

    "입력을 올바르게 넣었을 때 request()가 잘 되는가?" {
        val jsons = ArrivalJsonApi.request("22", "DGB7021025800", "DGB3000719000")
        println(jsons.joinToString("\n", "\n"))
        jsons.isNotEmpty() shouldBe true
    }

    "입력을 이상하게 넣었을 때 request()가 빈 결과값을 return하는가?" {
        val jsons = ArrivalJsonApi.request("22", "", "")
        println(jsons.joinToString("\n", "\n"))
        jsons.isEmpty() shouldBe true
    }
})