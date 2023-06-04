package com.example.catchingbus

import com.example.catchingbus.model.json.CityJsonApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class CityJsonApiTest {

    @Test
    fun requestCorrectly_isNotEmpty() = runTest {
        val jsons = CityJsonApi.request()

        val message = jsons.joinToString("\n", "\n")
        println(message)

        assertTrue(jsons.isNotEmpty())
    }
}