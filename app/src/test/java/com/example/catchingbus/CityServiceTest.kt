package com.example.catchingbus

import com.example.catchingbus.model.CityService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CityServiceTest {

    @Test
    fun search_isNotEmpty() = runTest {
        val cities = CityService.search()

        val message = cities.joinToString("\n", "\n")
        println(message)

        assertTrue(cities.isNotEmpty())
    }
}