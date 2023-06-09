package com.example.catchingbus.data

//import kotlinx.datetime.LocalTime

data class Schedule(
    val startTime: kotlinx.datetime.LocalTime,
    val endTime: kotlinx.datetime.LocalTime
)