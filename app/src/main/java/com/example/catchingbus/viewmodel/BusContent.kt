package com.example.catchingbus.viewmodel

import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Favorite

data class BusContent(
    val bus: Bus,
    val arrivalInfo: ArrivalInfo?,
    var favorite: Favorite? //내가고침
)