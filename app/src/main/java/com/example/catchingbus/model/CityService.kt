package com.example.catchingbus.model

import com.example.catchingbus.data.City
import com.example.catchingbus.model.json.CityJsonApi

object CityService {
    suspend fun search(): List<City> {
        val jsons = CityJsonApi.request()
        return jsons.map { City(it.citycode, it.cityname) }
    }
}