package com.example.catchingbus.model

import com.example.catchingbus.data.Favorite
import com.example.catchingbus.model.json.JsonFileRepo

object FavoriteRepo: JsonFileRepo<Favorite>(Favorite::class,
    "favorites.txt"
) {
    override suspend fun remove(element: Favorite) {
        super.remove(element)
        ScheduleRepo.remove(element)
    }
}