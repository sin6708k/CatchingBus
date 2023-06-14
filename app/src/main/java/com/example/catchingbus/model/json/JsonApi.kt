package com.example.catchingbus.model.json

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

abstract class JsonApi(
    private val endPoint: String,
    private val func: String
) {
    private lateinit var serviceKey: String
    val urlBase get() = "${endPoint}/${func}?serviceKey=${serviceKey}&_type=json"

    fun initialize(serviceKey: String) {
        this.serviceKey = serviceKey
    }

    // API로부터 JSON 데이터를 String으로 받는다.
    suspend fun download(urlParams: String): String {
        return try {
            val url = URL(urlBase + urlParams)
            val buffer = StringBuffer()

            withContext(Dispatchers.IO) {
                url.openConnection().getInputStream().use { stream ->
                    InputStreamReader(stream).use { streamReader ->
                        BufferedReader(streamReader).use { bufferReader ->
                            while (true) buffer.append(bufferReader.readLine() ?: break)
                        }
                    }
                }
            }

            buffer.toString()
        } catch (e: Exception) {
            ""
        }
    }

    // String으로 받은 JSON 데이터를 JsonElement로 변환한다.
    fun parse(jsonString: String): JsonElement {
        val whole = JsonParser.parseString(jsonString)
        val response = whole.asJsonObject.get("response")
        val header = response.asJsonObject.get("header")

        val resultCode = header.asJsonObject.get("resultCode")
        if (resultCode.asInt != 0) {
            return JsonNull.INSTANCE
        }

        val body = response.asJsonObject.get("body")
        val items = body.asJsonObject.get("items")

        return if (items.isJsonObject) {
            items.asJsonObject.get("item")
        } else {
            JsonNull.INSTANCE
        }
    }
}