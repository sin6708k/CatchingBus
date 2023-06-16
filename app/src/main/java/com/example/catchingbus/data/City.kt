package com.example.catchingbus.data

data class City(
    val code: Int,
    val name: String
) {
    companion object {
        val default = City(21, "부산광역시")
        // City(22, "대구광역시")
    }

    override fun hashCode(): Int {
        return code.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is City) {
            this.code == other.code
        } else {
            false
        }
    }
}
