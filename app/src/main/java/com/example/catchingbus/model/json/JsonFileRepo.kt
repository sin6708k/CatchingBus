package com.example.catchingbus.model.json

import android.util.Log
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

    // StateFlow는 mutableList를 사용할 수 없다.
    protected val _data = MutableStateFlow<List<T>>(emptyList())
    val data: StateFlow<List<T>> = _data

    protected val mutex = Mutex()

    suspend fun load(fileDirPath: String) {
        mutex.withLock {
            file = JsonFile(Path(fileDirPath, fileName))
            val jsonElement = file.load()
            Log.d("problem","load , ${jsonElement}")
            _data.value = Json.deserialize(clazz, jsonElement)
        }
    }

    protected suspend fun save() {
        val jsonString = Json.serialize(clazz, data.value)
        Log.d("problem","save , ${jsonString}")
        file.save(jsonString)
    }

    suspend fun clear() {
        mutex.withLock {
            _data.value = emptyList()
        }
        save()
    }
    suspend fun add(element: T) {
        mutex.withLock {
            if (data.value.indexOf(element) == -1) {
                _data.value = data.value.plus(element)
            }
        }
        save()
    }

    open suspend fun remove(element: T) {
        mutex.withLock {
            _data.value = data.value.minus(element)
        }
        save()
    }
}