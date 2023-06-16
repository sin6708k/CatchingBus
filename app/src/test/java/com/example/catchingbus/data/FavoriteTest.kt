package com.example.catchingbus.data

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class FavoriteTest: StringSpec({

    lateinit var favorite1: Favorite
    lateinit var favorite2: Favorite

    beforeTest {
        favorite1 = Favorite(
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
        favorite2 = Favorite(
            station = Station(
                id = "DGB7021025800",
                name = "",
                latitude = 0.0,
                longitude = 0.0
            ),
            bus = Bus(
                id = "DGB3000719000",
                name = "0",
                intervalTime = 0.toDuration(DurationUnit.MINUTES),
                type = ""
            )
        )
    }

    "id는 같은데 그외의 값이 다를 때 동등한 객체로 취급되는가?" {
        favorite1 shouldBeEqual favorite2
    }

    "id는 같은데 그외의 값이 다를 때 map에서 같은 key로 취급되는가?" {
        val map = mutableMapOf<Bus, Favorite>()
        map[favorite1.bus] = favorite1

        val favoriteInMap = map[favorite2.bus]
        if (favoriteInMap != null) {
            favoriteInMap shouldBeEqual favorite1
        } else {
            true shouldBeEqual false
        }
    }
})