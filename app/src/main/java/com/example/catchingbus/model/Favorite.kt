package com.example.catchingbus.model

import kotlin.time.Duration

data class Favorite(
    val station: Station,
    val bus: Bus,
    val alarmTime: Duration,
    val alarmActiveSchedules: MutableList<Schedule>
)