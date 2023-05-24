package com.example.catchingbus.data

import java.time.LocalTime

data class Schedule(
    val startTime: LocalTime,
    val endTime: LocalTime
)