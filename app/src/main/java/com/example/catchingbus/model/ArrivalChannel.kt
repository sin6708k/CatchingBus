package com.example.catchingbus.model

import com.example.catchingbus.data.ArrivalChannelMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.DurationUnit

// ArrivalInfo를 주기적으로 수신하는 객체
object ArrivalChannel {
    val message: SharedFlow<ArrivalChannelMessage> get() = _message
    private val _message = MutableSharedFlow<ArrivalChannelMessage>()

    fun start(
        period: Duration,
        remainingTimeToSend: Duration = Duration.INFINITE
    ) {
        fixedRateTimer(period = period.toLong(DurationUnit.MILLISECONDS)) {
            runTask(remainingTimeToSend)
        }
    }

    private fun runTask(remainingTimeToSend: Duration) = CoroutineScope(Dispatchers.Default).launch {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time

        val activeSchedules = ScheduleRepo.data.value.filter {
            now > it.startTime && now < it.endTime
        }
        activeSchedules.forEach {
            val arrivalInfo = ArrivalInfoService.search(it.favorite.station, it.favorite.bus)

            val remainingTime = arrivalInfo.remainingTimes.firstOrNull()
            if (remainingTime != null) {
                if (remainingTime < remainingTimeToSend) {
                    _message.emit(ArrivalChannelMessage(arrivalInfo))
                }
            }
        }
    }
}