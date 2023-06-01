package com.example.catchingbus.data

import kotlin.time.Duration

data class Favorite(
    val station: Station,
    val bus: Bus,
    val alarmTime: Duration,
    val alarmActiveSchedules: MutableList<Schedule>
)