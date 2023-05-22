package com.example.catchingbus.model.json

data class ArrivalJson(
    val arrprevstationcnt: Int,
    val arrtime: Int,
    val nodeid: String,
    val nodenm: String,
    val routeid: String,
    val routeno: String,
    val routetp: String
): JsonFromApi