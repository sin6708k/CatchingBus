package com.example.catchingbus.model.json

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import kotlin.time.Duration
import kotlin.time.DurationUnit

object DurationSerializer: JsonSerializer<Duration> {
    override fun serialize(
        src: Duration?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return if (src != null) {
            JsonPrimitive(src.toInt(DurationUnit.SECONDS))
        } else {
            JsonNull.INSTANCE
        }
    }
}