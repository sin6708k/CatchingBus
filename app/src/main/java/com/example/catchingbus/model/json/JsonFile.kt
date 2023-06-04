package com.example.catchingbus.model.json

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.nio.file.Path

class JsonFile(path: Path) {
    private val file = path.toFile()

    suspend fun load(): JsonElement {
        val jsonString = read()
        return JsonParser.parseString(jsonString)
    }

    suspend fun save(jsonString: String) {
        withContext(Dispatchers.Default) {
            file.printWriter().use { writer ->
                writer.print(jsonString)
            }
        }
    }

    private suspend fun read(): String {
        return try {
            val buffer = StringBuffer()

            withContext(Dispatchers.Default) {
                file.inputStream().use { stream ->
                    stream.reader().use { reader ->
                        reader.buffered().use { buffered ->
                            while (true) buffer.append(buffered.readLine() ?: break)
                        }
                    }
                }
            }

            buffer.toString()
        } catch (e: Exception) {
            ""
        }
    }
}