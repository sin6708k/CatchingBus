package com.example.catchingbus.model

import com.example.catchingbus.data.ArrivalChannelMessage
import com.example.catchingbus.data.Favorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.DurationUnit

// ArrivalInfo를 주기적으로 수신하는 객체
object ArrivalChannel {
    private var timer: Timer? = null

    val message: SharedFlow<ArrivalChannelMessage> get() = _message
    private val _message = MutableSharedFlow<ArrivalChannelMessage>()

    private val passCounts = mutableMapOf<Favorite, Int>()

    fun start(
        period: Duration,
        remainingTimeToSend: Duration = Duration.INFINITE,
        passCountWhenHit: Int = 0
    ) {
        timer?.cancel()
        timer = fixedRateTimer(period = period.toLong(DurationUnit.MILLISECONDS)) {
            runTask(remainingTimeToSend, passCountWhenHit)
        }
    }

    private fun runTask(
        remainingTimeToSend: Duration,
        passCountWhenHit: Int
    ) = CoroutineScope(Dispatchers.Default).launch {

        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time

        val activeSchedules = ScheduleRepo.data.value.filter {
            now > it.startTime && now < it.endTime
        }
        activeSchedules.forEach { schedule ->

            val passCount = passCounts[schedule.favorite] ?: 0
            if (passCount <= 0) {
                val arrivalInfo = ArrivalInfoService.search(
                    schedule.favorite.station,
                    schedule.favorite.bus
                )
                val remainingTime = arrivalInfo.remainingTimes.firstOrNull()
                if (remainingTime != null && remainingTime <= remainingTimeToSend) {
                    _message.emit(ArrivalChannelMessage(arrivalInfo))
                    passCounts[schedule.favorite] = passCountWhenHit
                }
            } else {
                passCounts[schedule.favorite] = passCount - 1
            }
        }
    }
}