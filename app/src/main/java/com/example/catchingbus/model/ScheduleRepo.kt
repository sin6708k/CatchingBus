package com.example.catchingbus.model

import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Schedule
import com.example.catchingbus.model.json.JsonFileRepo
import kotlinx.coroutines.sync.withLock

object ScheduleRepo: JsonFileRepo<Schedule>(Schedule::class,
    "favorites.txt"
) {
    suspend fun remove(favorite: Favorite) {
        mutex.withLock {
            _data.value = dataValue.also {
                it.removeIf { schedule ->
                    schedule.favorite == favorite
                }
            }
        }
        save()
    }
}