package com.example.catchingbus.model.json

import java.net.URLEncoder.encode

object StationJsonApi: JsonApi(
    "https://apis.data.go.kr/1613000/BusSttnInfoInqireService",
    "getSttnNoList"
) {
    fun request(cityCode: String, nodeNm: String): List<StationJson> {
        val jsonString = download(
            "&cityCode=${cityCode}&nodeNm=${encode(nodeNm, "UTF-8")}&pageNo=1&numOfRows=10")
        val jsonElement = parse(jsonString)
        return Json.deserialize(StationJson::class, jsonElement)
    }
}