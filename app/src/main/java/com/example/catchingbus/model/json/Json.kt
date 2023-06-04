package com.example.catchingbus.model.json

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.datetime.LocalTime
import kotlin.reflect.KClass

object Json {
    private val gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(LocalTime::class.java, LocalTimeDeserializer)
            .registerTypeAdapter(LocalTime::class.java, LocalTimeSerializer)
            .setPrettyPrinting()
            .create()
    }

    fun <T : Any> deserialize(clazz: KClass<T>, jsonElement: JsonElement): List<T> {
        return try {
            if (jsonElement.isJsonArray) {
                gson.fromJson(
                    jsonElement,
                    TypeToken.getParameterized(List::class.java, clazz.java).type
                )
            } else if (jsonElement.isJsonObject) {
                listOf(gson.fromJson(jsonElement, TypeToken.get(clazz.java).type))
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun <T : Any> serialize(clazz: KClass<T>, list: List<T>): String {
        return try {
            gson.toJson(
                list,
                TypeToken.getParameterized(List::class.java, clazz.java).type
            )
        } catch (e: Exception) {
            ""
        }
    }
}