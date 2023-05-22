package com.example.catchingbus.model

class AlarmMessage(private val arrivalInfo: ArrivalInfo) {
    override fun toString(): String {
        return arrivalInfo.toString()
    }
}