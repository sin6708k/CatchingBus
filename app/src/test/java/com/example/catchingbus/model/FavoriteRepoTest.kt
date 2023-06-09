package com.example.catchingbus.model

import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Station
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class FavoriteRepoTest: StringSpec({

    beforeSpec {
        FavoriteRepo.load()
    }

    "clear" {
        FavoriteRepo.clear()
        FavoriteRepo.data.value.isEmpty() shouldBe true
    }

    "add" {
        val station = Station(
            "DGB7021025800",
            "경북대학교북문앞",
            35.89294,
            128.60996
        )
        val bus = Bus(
            "DGB3000719000",
            "719",
            0,
            "간선버스"
        )
        val favorite = Favorite(station, bus)
        FavoriteRepo.add(favorite)
        FavoriteRepo.data.value.isNotEmpty() shouldBe true
    }

    "save and load" {
        FavoriteRepo.save()
        FavoriteRepo.load()
        println(FavoriteRepo.data.value.joinToString("\n", "\n"))
        FavoriteRepo.data.value.isNotEmpty() shouldBe true
    }
})