package com.example.catchingbus.viewmodel

import com.example.catchingbus.model.ArrivalChannel
import com.example.catchingbus.model.FavoriteRepo
import com.example.catchingbus.model.ScheduleRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object GlobalInitializer {
    fun initialize(fileDirPath: String) {
        CoroutineScope(Dispatchers.Default).launch {
            FavoriteRepo.load(fileDirPath)
            ScheduleRepo.load(fileDirPath)
            ArrivalChannel.start(30.toDuration(DurationUnit.SECONDS))
        }
    }
}