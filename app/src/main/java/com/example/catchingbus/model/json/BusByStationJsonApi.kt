package com.example.catchingbus.model.json

object BusByStationJsonApi: JsonApi(
    "https://apis.data.go.kr/1613000/BusSttnInfoInqireService",
    "getSttnThrghRouteList"
) {
    fun request(cityCode: String, nodeid: String): List<BusByStationJson> {
        val jsonString = download("&cityCode=${cityCode}&nodeid=${nodeid}")
        val jsonElement = parse(jsonString)
        return Json.deserialize(BusByStationJson::class, jsonElement)
    }
}