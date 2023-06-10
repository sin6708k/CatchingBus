package com.example.catchingbus.data

import kotlin.time.Duration

data class Bus(
    val id: String,
    val name: String,
    val intervalTime: Duration,
    val type: String
) {
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Bus) {
            this.id == other.id
        } else {
            false
        }
    }
}