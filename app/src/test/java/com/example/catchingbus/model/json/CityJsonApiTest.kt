package com.example.catchingbus.model.json

import com.example.catchingbus.viewmodel.GlobalInitializer
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CityJsonApiTest: StringSpec({

    beforeSpec {
        CityJsonApi.initialize(GlobalInitializer.SERVICE_KEY)
    }

    "request()가 잘 되는가?" {
        val jsons = CityJsonApi.request()
        println(jsons.joinToString("\n", "\n"))
        jsons.isNotEmpty() shouldBe true
    }
})