package com.example.catchingbus.model

import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Station
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class FavoriteRepoTest: StringSpec({

    beforeTest {
        FavoriteRepo.load("")
    }

    "clear" {
        FavoriteRepo.clear()
        println(FavoriteRepo.data.value.joinToString("\n", "\n"))
        FavoriteRepo.data.value.isEmpty() shouldBe true
    }

    "add" {
        val station = Station(
            id = "DGB7021025800",
            name = "경북대학교북문앞",
            latitude = 35.89294,
            longitude = 128.60996
        )
        val bus = Bus(
            id = "DGB3000719000",
            name = "719",
            intervalTime = 0.toDuration(DurationUnit.MINUTES),
            type = "간선버스"
        )
        val favorite = Favorite(station, bus)
        FavoriteRepo.add(favorite)
        println(FavoriteRepo.data.value.joinToString("\n", "\n"))
        FavoriteRepo.data.value.isNotEmpty() shouldBe true
    }
})