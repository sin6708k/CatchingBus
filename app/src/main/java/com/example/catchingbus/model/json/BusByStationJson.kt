package com.example.catchingbus.model.json

data class BusByStationJson(
    val endnodenm: String,
    val routeid: String,
    val routeno: String,
    val routetp: String,
    val startnodenm: String
): JsonFromApi