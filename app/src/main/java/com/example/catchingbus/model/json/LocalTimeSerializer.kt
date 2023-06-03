package com.example.catchingbus.model.json

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import kotlinx.datetime.LocalTime
import java.lang.reflect.Type

object LocalTimeSerializer: JsonSerializer<LocalTime> {

    override fun serialize(
        src: LocalTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return if (src != null) {
            JsonPrimitive(src.toSecondOfDay())
        } else {
            JsonNull.INSTANCE
        }
    }
}