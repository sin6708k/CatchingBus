package com.example.catchingbus.model.json

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.nio.file.Path

// JSON 데이터가 저장된 파일을 읽고 쓰는 객체
class JsonFile(path: Path) {
    private val file = path.toFile()

    suspend fun load(): JsonElement {
        val jsonString = read()
        return JsonParser.parseString(jsonString)
    }

    suspend fun save(jsonString: String) {
        withContext(Dispatchers.IO) {
            file.printWriter().use { writer ->
                writer.print(jsonString)
            }
        }
    }

    private suspend fun read(): String {
        return try {
            val buffer = StringBuffer()

            withContext(Dispatchers.IO) {
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