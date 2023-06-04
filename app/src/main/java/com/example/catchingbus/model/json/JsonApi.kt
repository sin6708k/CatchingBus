package com.example.catchingbus.model.json

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

abstract class JsonApi(endPoint: String, func: String) {

    val urlBase = "${endPoint}/${func}?serviceKey=${SERVICE_KEY}&_type=json"

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

    companion object {
        private const val SERVICE_KEY = "TdvBzglDeCrmafwGrqqjOSj7ZnRi9AR5JMUTPozZRnaaqpUTnlYtwgFWeejzhWXLvboD81F1fsk%2FtL2Br9TEjA%3D%3D"
    }
}