package com.example.catchingbus.model.json

object BusJsonApi: JsonApi(
    "https://apis.data.go.kr/1613000/BusRouteInfoInqireService",
    "getRouteInfoIem"
) {
    fun request(cityCode: String, routeId: String): List<BusJson> {
        val jsonString = download("&cityCode=${cityCode}&routeId=${routeId}")
        val jsonElement = parse(jsonString)
        return Json.deserialize(BusJson::class, jsonElement)
    }
}