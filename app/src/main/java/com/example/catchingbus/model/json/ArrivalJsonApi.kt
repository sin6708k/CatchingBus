package com.example.catchingbus.model.json

object ArrivalJsonApi: JsonApi(
    "https://apis.data.go.kr/1613000/ArvlInfoInqireService",
    "getSttnAcctoSpcifyRouteBusArvlPrearngeInfoList"
) {
    suspend fun request(cityCode: String, nodeId: String, routeId: String): List<ArrivalJson> {
        val jsonString = download("&cityCode=${cityCode}&nodeId=${nodeId}&routeId=${routeId}")

        return if (jsonString.isNotEmpty()) {
            val jsonElement = parse(jsonString)
            Json.deserialize(ArrivalJson::class, jsonElement)
        } else {
            emptyList()
        }
    }
}