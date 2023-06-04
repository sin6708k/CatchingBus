package com.example.catchingbus

import com.example.catchingbus.model.json.StationJsonApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class StationJsonApiTest {

    @Test
    fun request_isNotEmpty() = runTest {
        val jsons = StationJsonApi.request("22", "경북대학교북문")

        val message = jsons.joinToString("\n", "\n")
        println(message)

        assertTrue(jsons.isNotEmpty())
    }
}