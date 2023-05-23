package com.example.catchingbus.model.json

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLEncoder.encode

object StationJsonApi: JsonApi(
    "https://apis.data.go.kr/1613000/BusSttnInfoInqireService",
    "getSttnNoList"
) {
    suspend fun request(cityCode: String, nodeNm: String): List<StationJson> {
        val encodedNodeNm = withContext(Dispatchers.IO) {
            encode(nodeNm, "UTF-8")
        }
        val jsonString = download("&cityCode=${cityCode}&nodeNm=${encodedNodeNm}&pageNo=1&numOfRows=10")
        val jsonElement = parse(jsonString)
        return Json.deserialize(StationJson::class, jsonElement)
    }
}