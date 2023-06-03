package com.example.catchingbus.model

import com.example.catchingbus.data.Favorite
import com.example.catchingbus.model.json.Json
import com.example.catchingbus.model.json.JsonFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.io.path.Path

object FavoriteRepo {
    private val file = JsonFile(Path("favorites.txt"))

    private val _data = MutableStateFlow(listOf<Favorite>())
    val data: StateFlow<List<Favorite>> = _data

    suspend fun load() {
        val jsonElement = file.load()
        _data.value = Json.deserialize(Favorite::class, jsonElement)
    }

    fun clear() {
        _data.value = emptyList()
    }

    fun add(favorite: Favorite) {
        _data.value = data.value.plus(favorite)
    }

    fun update(favorite: Favorite) {
        val filtered = data.value.filterNot {
            it.station == favorite.station && it.bus == favorite.bus
        }
        _data.value = filtered.plus(favorite)
    }

    fun remove(favorite: Favorite) {
        _data.value = data.value.minus(favorite)
    }

    suspend fun save() {
        val jsonString = Json.serialize(Favorite::class, data.value)
        file.save(jsonString)
    }
}