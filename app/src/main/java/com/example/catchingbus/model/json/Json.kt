package com.example.catchingbus.model.json

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlin.reflect.KClass

interface Json {
    companion object {
        private val gson by lazy { Gson() }

        fun <T: Json> deserialize(clazz: KClass<T>, jsonElement: JsonElement): List<T> {
            return try {
                if (jsonElement.isJsonArray) {
                    gson.fromJson(
                        jsonElement,
                        TypeToken.getParameterized(List::class.java, clazz.java).type
                    )
                } else if (jsonElement.isJsonObject) {
                    listOf(gson.fromJson(jsonElement, TypeToken.get(clazz.java).type))
                } else {
                    listOf()
                }
            } catch (e: Exception) {
                listOf()
            }
        }
    }
}
