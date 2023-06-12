package com.example.catchingbus.model

import com.example.catchingbus.data.ArrivalChannelMessage
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

object ArrivalChannel {
    private var job: Job? = null

    val message: SharedFlow<ArrivalChannelMessage> get() = _message
    private val _message = MutableSharedFlow<ArrivalChannelMessage>()

    fun start(delayTime: Duration) {
        job?.cancel()
        job = CoroutineScope(Dispatchers.Default).launch { run(delayTime) }
    }

    private suspend fun run(delayTime: Duration) {
        while (true) {
            val now = LocalTime.now()?.toKotlinLocalTime()
            if (now != null) {
                val activeSchedules = ScheduleRepo.data.value.filter {
                    now > it.startTime && now < it.endTime
                }
                activeSchedules.forEach {
                    _message.emit(
                        ArrivalChannelMessage(
                        arrivalInfo = ArrivalInfoService.search(it.favorite.station, it.favorite.bus)
                    )
                    )
                }
            }
            delay(delayTime)
        }
    }
}