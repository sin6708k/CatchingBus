package com.example.catchingbus.model

import com.example.catchingbus.data.Schedule
import kotlin.time.Duration

class TimeAlarm(
    val time: Duration,
    val activeSchedules: List<Schedule>

)