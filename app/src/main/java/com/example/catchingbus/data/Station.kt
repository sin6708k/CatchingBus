package com.example.catchingbus.data

data class Station(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double
) {
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Station) {
            this.id == other.id
        } else {
            false
        }
    }
}