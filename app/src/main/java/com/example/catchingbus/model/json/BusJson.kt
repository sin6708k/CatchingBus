package com.example.catchingbus.model.json

data class BusJson(
    val endnodenm: String,
    val endvehicletime: Int,
    val intervalsattime: Int,
    val intervalsuntime: Int,
    val intervaltime: Int,
    val routeid: String,
    val routeno: String,
    val routetp: String,
    val startnodenm: String,
    val startvehicletime: String
): JsonFromApi