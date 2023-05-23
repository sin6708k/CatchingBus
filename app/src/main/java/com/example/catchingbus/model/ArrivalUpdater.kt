package com.example.catchingbus.model

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow

class ArrivalUpdater(
    private val station: Station,
    private val bus: Bus,
    private val alarm: TimeAlarm? = null

): Runnable(alarm?.activeSchedules ?: listOf()) {

    val latest by lazy { MutableSharedFlow<ArrivalInfo>() }
    val alarmMessage by lazy { Channel<AlarmMessage>() }

    override suspend fun run(): RepeatCommand {
        val new = ArrivalInfo.search(station, bus)

        if (alarm != null) {
            if (new.remainingTimes.first() < alarm.time) {
                alarmMessage.send(AlarmMessage(new))
            }
        }
        return RepeatCommand(new.remainingTimes.first())
    }
}