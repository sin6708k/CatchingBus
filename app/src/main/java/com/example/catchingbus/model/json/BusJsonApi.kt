package com.example.catchingbus.model.json

object BusJsonApi: JsonApi(
    "https://apis.data.go.kr/1613000/BusRouteInfoInqireService",
    "getRouteInfoIem"
) {
    suspend fun request(cityCode: String, routeId: String): List<BusJson> {
        val jsonString = download("&cityCode=${cityCode}&routeId=${routeId}")

        return if (jsonString.isNotEmpty()) {
            val jsonElement = parse(jsonString)
            Json.deserialize(BusJson::class, jsonElement)
        } else {
            emptyList()
        }
    }
}