package com.example.catchingbus.model.json

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.io.path.Path
import kotlin.reflect.KClass

abstract class JsonFileRepo<T : Any>(
    private val clazz: KClass<T>,
    private val fileName: String
) {
    private lateinit var file: JsonFile

    protected val flowValue = mutableListOf<T>()
    protected val flow = MutableStateFlow<List<T>>(flowValue)
    val data: StateFlow<List<T>> = flow

    protected val mutex = Mutex()

    suspend fun load(fileDirPath: String) {
        mutex.withLock {
            file = JsonFile(Path(fileDirPath, fileName))
            val jsonElement = file.load()
            flow.value = Json.deserialize(clazz, jsonElement)
        }
    }

    protected suspend fun save() {
        val jsonString = Json.serialize(clazz, flowValue)
        file.save(jsonString)
    }

    suspend fun clear() {
        mutex.withLock {
            flow.value = flowValue.also { it.clear() }
        }
        save()
    }

    suspend fun add(element: T) {
        mutex.withLock {
            flow.value = flowValue.also { it.add(element) }
        }
        save()
    }

    open suspend fun remove(element: T) {
        mutex.withLock {
            flow.value = flowValue.also { it.remove(element) }
        }
        save()
    }
}