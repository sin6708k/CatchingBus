package com.example.catchingbus.model

import com.example.catchingbus.data.Schedule
import com.example.catchingbus.model.json.Json
import com.example.catchingbus.model.json.JsonFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.io.path.Path

object ScheduleRepo {
    private val file = JsonFile(Path("schedules.txt"))

    private val flowValue = mutableListOf<Schedule>()
    private val flow = MutableStateFlow<List<Schedule>>(flowValue)
    val data: StateFlow<List<Schedule>> = flow

    private val mutex = Mutex()

    suspend fun load() {
        mutex.withLock {
            val jsonElement = file.load()
            flow.value = Json.deserialize(Schedule::class, jsonElement)
        }
    }

    private suspend fun save() {
        val jsonString = Json.serialize(Schedule::class, flowValue)
        file.save(jsonString)
    }

    suspend fun clear() {
        mutex.withLock {
            flow.value = flowValue.also { it.clear() }
        }
        save()
    }

    suspend fun add(element: Schedule) {
        mutex.withLock {
            flow.value = flowValue.also { it.add(element) }
        }
        save()
    }

    suspend fun remove(element: Schedule) {
        mutex.withLock {
            flow.value = flowValue.also { it.remove(element) }
        }
        save()
    }
}