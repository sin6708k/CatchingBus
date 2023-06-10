package com.example.catchingbus.presentation

import com.example.catchingbus.data.AlarmMessage
import com.example.catchingbus.model.ArrivalInfoService
import com.example.catchingbus.model.ScheduleRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalTime
import java.time.LocalTime
import kotlin.time.Duration

object ArrivalAlarm {
    private var job: Job? = null

    val alarmMessage: SharedFlow<AlarmMessage> get() = _alarmMessage
    private val _alarmMessage = MutableSharedFlow<AlarmMessage>()

    fun start(delayTime: Duration) {
        job?.cancel()
        job = CoroutineScope(Dispatchers.Default).launch { run(delayTime) }
    }

    private suspend fun run(delayTime: Duration) {
        while (true) {
            val now = LocalTime.now().toKotlinLocalTime()
            val activeSchedules = ScheduleRepo.data.value.filter {
                now > it.startTime && now < it.endTime
            }
            activeSchedules.forEach {
                _alarmMessage.emit(
                    AlarmMessage(
                    arrivalInfo = ArrivalInfoService.search(it.favorite.station, it.favorite.bus)
                )
                )
            }
            delay(delayTime)
        }
    }
}