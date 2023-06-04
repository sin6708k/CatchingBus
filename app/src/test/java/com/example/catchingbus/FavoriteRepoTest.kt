package com.example.catchingbus

import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Station
import com.example.catchingbus.model.FavoriteRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteRepoTest {
    private val tempStation = Station(
        "DGB7021025800",
        "경북대학교북문앞",
        35.89294,
        128.60996
    )
    private val tempBus = Bus(
        "DGB3000719000",
        "719",
        0,
        "간선버스"
    )
    private val tempFavorite = Favorite(tempStation, tempBus)

    @Test
    fun testAdd() = runTest {
        FavoriteRepo.load()
        FavoriteRepo.clear()
        assertTrue(FavoriteRepo.data.value.isEmpty())

        FavoriteRepo.add(tempFavorite)
        assertTrue(FavoriteRepo.data.value.isNotEmpty())

        val message1 = FavoriteRepo.data.value.joinToString("\n", "\n")
        println(message1)

        FavoriteRepo.save()
        FavoriteRepo.load()

        val message2 = FavoriteRepo.data.value.joinToString("\n", "\n")
        println(message2)

        assertTrue(FavoriteRepo.data.value.isNotEmpty())
    }
}