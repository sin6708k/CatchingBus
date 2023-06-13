package com.example.catchingbus.model

import com.example.catchingbus.data.ArrivalChannelMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

// ArrivalInfo를 주기적으로 수신하는 객체
object ArrivalChannel {
    private var job: Job? = null

    val message: SharedFlow<ArrivalChannelMessage> get() = _message
    private val _message = MutableSharedFlow<ArrivalChannelMessage>()

    fun start(delayTime: Duration) {
        job?.cancel()
        job = CoroutineScope(Dispatchers.Default).launch {
            run(delayTime)
        }
    }

    private suspend fun run(delayTime: Duration) {
        while (true) {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time

            val activeSchedules = ScheduleRepo.data.value.filter {
                now > it.startTime && now < it.endTime
            }
            activeSchedules.forEach {
                val arrivalInfo = ArrivalInfoService.search(it.favorite.station, it.favorite.bus)
                _message.emit(ArrivalChannelMessage(arrivalInfo))
            }
            delay(delayTime)
        }
    }
}