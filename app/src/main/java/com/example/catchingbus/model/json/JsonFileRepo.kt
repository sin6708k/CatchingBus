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
            _data.value = data.value.apply { clear() }
        }
        save()
    }
    suspend fun add(element: T) {
        mutex.withLock {
            if (data.value.indexOf(element) == -1) {
                Log.d("problem", "JsonFileRepo.add() start\n * ${data.value}")
                _data.value = data.value.plus(element)
                Log.d("problem", "JsonFileRepo.add end\n * ${data.value})")
            }
        }
        save()
    }

    open suspend fun remove(element: T) {
        mutex.withLock {
            Log.d("problem", "JsonFileRepo.remove() start\n * ${data.value}")
            _data.value = data.value.minus(element)
            Log.d("problem", "JsonFileRepo.remove() end\n * ${data.value}")
        }
        save()
    }
}