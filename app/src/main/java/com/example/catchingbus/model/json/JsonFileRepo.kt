package com.example.catchingbus.model.json

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.nio.file.Path
import kotlin.reflect.KClass

// JsonFile로부터 받은 데이터를 다른 객체에 제공하는 객체
abstract class JsonFileRepo<T : Any>(private val clazz: KClass<T>) {
    private lateinit var file: JsonFile

    // StateFlow는 mutableList를 사용할 수 없다.
    protected val _data = MutableStateFlow<List<T>>(emptyList())
    val data: StateFlow<List<T>> = _data

    protected val mutex = Mutex()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            data.collectLatest {
                save(it)
            }
        }
    }

    suspend fun load(filePath: Path) {
        mutex.withLock {
            file = JsonFile(filePath)
            val jsonElement = file.load()
            _data.value = Json.deserialize(clazz, jsonElement)
        }
    }

    private suspend fun save(list: List<T>) {
        val jsonString = Json.serialize(clazz, list)
        file.save(jsonString)
    }

    suspend fun clear() {
        mutex.withLock {
            _data.value = emptyList()
        }
    }

    suspend fun add(element: T) {
        mutex.withLock {
            if (data.value.indexOf(element) == -1) {
                _data.value = data.value.plus(element)
            }
        }
    }

    open suspend fun remove(element: T) {
        mutex.withLock {
            _data.value = data.value.minus(element)
        }
    }
}