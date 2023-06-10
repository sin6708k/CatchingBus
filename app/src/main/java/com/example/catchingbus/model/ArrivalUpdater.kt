package com.example.catchingbus.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Station
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.time.Duration

class ArrivalUpdater(
    private val station: Station,
    private val bus: Bus,
    private val alarm: TimeAlarm? = null

): RepeatedRunner(alarm?.activeSchedules ?: listOf()) {

    val latest by lazy { MutableSharedFlow<ArrivalInfo>() }
    val alarmMessage by lazy { Channel<AlarmMessage>() }

    override suspend fun run(): RepeatCommand {
        val new = ArrivalInfoService.search(station, bus)

        if (alarm != null) {
            if (new.remainingTimes.first() < alarm.time) {
                alarmMessage.send(AlarmMessage(new))
            }
        }
        return RepeatCommand(new.remainingTimes.first())
    }
}


 /*
class ArrivalUpdater(
    private val station: Station,
    private val bus: Bus,
    private val alarm: TimeAlarm? = null
) : RepeatedRunner(alarm?.activeSchedules ?: listOf()) {

    val latest by lazy { MutableSharedFlow<ArrivalInfo>() }
    val alarmMessage by lazy { Channel<AlarmMessage>() }

    override suspend fun run(): RepeatCommand<LiveData<List<Duration>>> {
        val new = ArrivalInfoService.search(station, bus)

        if (alarm != null) {
            if (new.remainingTimes.isNotEmpty() && new.remainingTimes[0] < alarm.time) {
                alarmMessage.send(AlarmMessage(new))
            }
        }
        return RepeatCommand(new.remainingTimes)
    }
}


 */