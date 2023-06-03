package com.example.catchingbus

import com.example.catchingbus.model.json.ArrivalJsonApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class ArrivalJsonApiTest {

    @Test
    fun requestCorrectly_isNotEmpty() = runTest {
        val jsons = ArrivalJsonApi.request("22", "DGB7021025800", "DGB3000719000")

        val message = jsons.joinToString("\n", "\n")
        println(message)

        assertTrue(jsons.isNotEmpty())
    }

    @Test
    fun requestIncorrectly_isEmpty() = runTest {
        val jsons = ArrivalJsonApi.request("22", "", "")

        val message = jsons.joinToString("\n", "\n")
        println(message)

        assertTrue(jsons.isEmpty())
    }
}