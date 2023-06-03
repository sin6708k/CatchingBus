package com.example.catchingbus.data

import kotlinx.datetime.LocalTime

data class Schedule(
    val startTime: LocalTime,
    val endTime: LocalTime
)