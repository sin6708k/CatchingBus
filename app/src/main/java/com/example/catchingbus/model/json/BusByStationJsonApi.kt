package com.example.catchingbus.model.json

object BusByStationJsonApi: JsonApi(
    "https://apis.data.go.kr/1613000/BusSttnInfoInqireService",
    "getSttnThrghRouteList"
) {
    suspend fun request(cityCode: String, nodeid: String): List<BusByStationJson> {
        val jsonString = download("&cityCode=${cityCode}&nodeid=${nodeid}")

        return if (jsonString.isNotEmpty()) {
            val jsonElement = parse(jsonString)
            Json.deserialize(BusByStationJson::class, jsonElement)
        } else {
            emptyList()
        }
    }
}