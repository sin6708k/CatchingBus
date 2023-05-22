package com.example.catchingbus.model

import kotlinx.coroutines.channels.Channel
import kotlin.properties.Delegates

class ArrivalUpdater(
    private val station: Station,
    private val bus: Bus,
    private val alarm: TimeAlarm? = null

): Runnable(alarm?.activeSchedules ?: listOf()) {

    val latest by Delegates.observable(ArrivalInfo(station, bus, listOf())) { _, _, _ ->
        onLatestListener?.also { it() }
    }
    val alarmMessage by lazy { Channel<AlarmMessage>() }

    var onLatestListener: (() -> Unit)? = null

    override suspend fun run(): RepeatCommand {
        val new = ArrivalInfo.search(station, bus)

        alarm?.also { alarm ->
            if (new.remainingTimes.first() < alarm.time) {
                alarmMessage.send(AlarmMessage(new))
            }
        }
        return RepeatCommand(new.remainingTimes.first())
    }
}