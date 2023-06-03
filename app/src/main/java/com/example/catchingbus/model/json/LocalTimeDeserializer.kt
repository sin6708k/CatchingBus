package com.example.catchingbus.model.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import kotlinx.datetime.LocalTime
import java.lang.reflect.Type

object LocalTimeDeserializer: JsonDeserializer<LocalTime> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalTime {
        return if (json != null) {
            LocalTime.fromSecondOfDay(json.asInt)
        } else {
            LocalTime.fromSecondOfDay(0)
        }
    }
}