package com.example.catchingbus.model

import kotlin.time.Duration

class TimeAlarm(
    val time: Duration,
    val activeSchedules: List<Schedule>
)