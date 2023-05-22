package com.example.catchingbus.model.json

import android.util.Log
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

abstract class JsonApi(endPoint: String, func: String) {

    val urlBase = "${endPoint}/${func}?serviceKey=${SERVICE_KEY}&_type=json"

    fun download(urlParams: String): String {
        return try {
            val url = URL(urlBase + urlParams)
            val buffer = StringBuffer()

            url.openConnection().getInputStream().use { stream ->
                InputStreamReader(stream).use { streamReader ->
                    BufferedReader(streamReader).use { bufferReader ->
                        while (true) buffer.append(bufferReader.readLine() ?: break)
                    }
                }
            }

            buffer.toString()
        } catch (e: Exception) {
            Log.e("Exception", "Exception occurred: ${e.javaClass.simpleName}, Message: ${e.message}")
            ""
        }
    }

    fun parse(jsonString: String): JsonElement {
        //Log.d("problem","오류발생 : ${jsonString}")
        /*
        if (jsonString.isNullOrEmpty()) {
            Log.d("problem","오류 발생: ${jsonString}")
            throw IllegalArgumentException("Invalid JSON string: $jsonString")
        }
         */
        val whole = JsonParser.parseString(jsonString).asJsonObject
        val response = whole.get("response").asJsonObject
        val body = response.get("body").asJsonObject
        val items = body.get("items").asJsonObject
        return items.get("item")
    }

    companion object {
        private const val SERVICE_KEY = "TdvBzglDeCrmafwGrqqjOSj7ZnRi9AR5JMUTPozZRnaaqpUTnlYtwgFWeejzhWXLvboD81F1fsk%2FtL2Br9TEjA%3D%3D"
    }
}