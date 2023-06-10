package com.example.catchingbus.model.json

import android.util.Log
import com.example.catchingbus.viewmodel.SearchViewModel
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

    protected val dataValue = mutableListOf<T>()
    protected val _data = MutableStateFlow<List<T>>(dataValue)
    val data: StateFlow<List<T>> = _data

    protected val mutex = Mutex()

    suspend fun load(fileDirPath: String) {
        mutex.withLock {
            file = JsonFile(Path(fileDirPath, fileName))
            val jsonElement = file.load()
            _data.value = Json.deserialize(clazz, jsonElement)
        }
    }

    protected suspend fun save() {
        val jsonString = Json.serialize(clazz, dataValue)
        file.save(jsonString)
    }

    suspend fun clear() {
        mutex.withLock {
            _data.value = dataValue.apply { clear() }
        }
        save()
    }
    suspend fun add(element: T) {
        mutex.withLock {
            if (dataValue.indexOf(element) == -1) {
                Log.d("problem", "JsonFileRepo.add() start\n * ${data.value}")
                _data.value = dataValue.apply { add(element) }.map { it }
                Log.d("problem", "JsonFileRepo.add end\n * ${data.value})")
            }
        }
        save()
    }

    open suspend fun remove(element: T) {
        mutex.withLock {
            Log.d("problem", "JsonFileRepo.remove() start\n * ${data.value}")
            _data.value = dataValue.apply { remove(element) }.map { it }
            Log.d("problem", "JsonFileRepo.remove() end\n * ${data.value}")
        }
        save()
    }
}