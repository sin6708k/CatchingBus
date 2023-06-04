package com.example.catchingbus

import com.example.catchingbus.model.json.BusJsonApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class BusJsonApiTest {

    @Test
    fun requestCorrectly_isNotEmpty() = runTest {
        val jsons = BusJsonApi.request("22", "DGB3000719000")

        val message = jsons.joinToString("\n", "\n")
        println(message)

        assertTrue(jsons.isNotEmpty())
    }

    @Test
    fun requestIncorrectly_isEmpty() = runTest {
        val jsons = BusJsonApi.request("22", "")

        val message = jsons.joinToString("\n", "\n")
        println(message)

        assertTrue(jsons.isEmpty())
    }
}