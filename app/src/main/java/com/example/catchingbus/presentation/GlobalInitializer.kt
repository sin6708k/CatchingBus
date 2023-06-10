package com.example.catchingbus.presentation

import com.example.catchingbus.model.FavoriteRepo
import com.example.catchingbus.model.ScheduleRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object GlobalInitializer {
    fun initialize() {
        CoroutineScope(Dispatchers.Default).launch {
            FavoriteRepo.load()
            ScheduleRepo.load()
        }
        ArrivalAlarm.start(30.toDuration(DurationUnit.SECONDS))
    }
}