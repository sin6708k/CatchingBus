package com.example.catchingbus.model

import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Station
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class FavoriteRepoTest: StringSpec({

    lateinit var favorite: Favorite

    beforeTest {
        FavoriteRepo.load("")

        CoroutineScope(Dispatchers.Default).launch {
            FavoriteRepo.data.collectLatest {
                println(it.joinToString("\n * ", "collectLatest\n * "))
            }
        }

        favorite = Favorite(
            station = Station(
                id = "DGB7021025800",
                name = "경북대학교북문앞",
                latitude = 35.89294,
                longitude = 128.60996
            ),
            bus = Bus(
                id = "DGB3000719000",
                name = "719",
                intervalTime = 0.toDuration(DurationUnit.MINUTES),
                type = "간선버스"
            )
        )
    }

    "clear" {
        FavoriteRepo.clear()
        println(FavoriteRepo.data.value.joinToString("\n * ", "clear\n * "))
        FavoriteRepo.data.value.isEmpty() shouldBe true
        delay(1.toDuration(DurationUnit.SECONDS))
    }

    "add" {
        FavoriteRepo.add(favorite)
        println(FavoriteRepo.data.value.joinToString("\n * ", "add\n * "))
        FavoriteRepo.data.value.isNotEmpty() shouldBe true
        delay(1.toDuration(DurationUnit.SECONDS))
    }
})