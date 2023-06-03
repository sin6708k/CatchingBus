package com.example.catchingbus.data

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

data class Favorite(
    val station: Station,
    val bus: Bus,
    val alarmTime: Duration = 5.toDuration(DurationUnit.SECONDS),
    val alarmActiveSchedules: MutableList<Schedule> = mutableListOf()
)