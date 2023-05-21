package com.example.catchingbus.model.json

object ArrivalJsonApi: JsonApi(
    "https://apis.data.go.kr/1613000/ArvlInfoInqireService",
    "getSttnAcctoSpcifyRouteBusArvlPrearngeInfoList"
) {
    fun request(cityCode: String, nodeId: String, routeId: String): List<ArrivalJson> {
        val jsonString = download("&cityCode=${cityCode}&nodeId=${nodeId}&routeId=${routeId}")
        val jsonElement = parse(jsonString)
        return Json.deserialize(ArrivalJson::class, jsonElement)
    }
}