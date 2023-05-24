package com.example.catchingbus.data

data class City(
    val code: Int,
    val name: String
) {
    companion object {
        val default = City(22, "대구광역시")
    }
}
