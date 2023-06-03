package com.example.catchingbus

import com.example.catchingbus.model.StationService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class StationServiceTest {

    @Test
    fun search_isNotEmpty() = runTest {
        val stations = StationService.search("경북대학교북문")

        val message = stations.joinToString("\n", "\n")
        println(message)

        assertTrue(stations.isNotEmpty())
    }
}