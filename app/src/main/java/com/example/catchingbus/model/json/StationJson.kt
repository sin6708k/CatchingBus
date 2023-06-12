package com.example.catchingbus.model.json

data class StationJson(
    val gpslati: Double,
    val gpslong: Double,
    val nodeid: String,
    val nodenm: String,
    val nodeno: Int
): JsonFromApi