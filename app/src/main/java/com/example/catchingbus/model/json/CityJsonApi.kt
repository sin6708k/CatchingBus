package com.example.catchingbus.model.json

object CityJsonApi: JsonApi(
    "http://apis.data.go.kr/1613000/BusSttnInfoInqireService",
    "getCtyCodeList"
) {
    suspend fun request(): List<CityJson> {
        val jsonString = download("")
        val jsonElement = parse(jsonString)
        return Json.deserialize(CityJson::class, jsonElement)
    }
}