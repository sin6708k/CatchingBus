package com.example.catchingbus.data

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

data class Favorite(
    val station: Station,
    val bus: Bus,
    val alarmTime: Duration = 5.toDuration(DurationUnit.SECONDS), //알람을 울릴지 말지 확인
    val alarmActiveSchedules: MutableList<Schedule> = mutableListOf() //xx:00~ yy: 00~
)