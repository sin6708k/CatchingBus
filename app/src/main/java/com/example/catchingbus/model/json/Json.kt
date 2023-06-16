package com.example.catchingbus.model.json

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.datetime.LocalTime
import kotlin.reflect.KClass
import kotlin.time.Duration

object Json {
    private val gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(Duration::class.java, DurationDeserializer)
            .registerTypeAdapter(Duration::class.java, DurationSerializer)
            .registerTypeAdapter(LocalTime::class.java, LocalTimeDeserializer)
            .registerTypeAdapter(LocalTime::class.java, LocalTimeSerializer)
            .setPrettyPrinting()
            .create()
    }

    // JSON 데이터를 우리가 사용할 수 있는 형식의 리스트로 변환한다.
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

    // 리스트를 JSON 데이터로 변환한다.
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