package com.example.catchingbus.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catchingbus.model.ArrivalInfo
import com.example.catchingbus.model.Bus
import com.example.catchingbus.model.Station

class SearchViewModel: ViewModel() {

    val searchWord: MutableLiveData<String> by lazy {
        MutableLiveData("")
    }

    val stations: LiveData<List<Station>> get() = _stations
    private val _stations: MutableLiveData<List<Station>> by lazy {
        MutableLiveData(listOf())
    }

    val stationSelected: MutableLiveData<Station?> by lazy {
        MutableLiveData(null)
    }

    val arrivalInfoes: LiveData<List<ArrivalInfo>> get() = _arrivalInfoes
    private val _arrivalInfoes: MutableLiveData<List<ArrivalInfo>> by lazy {
        MutableLiveData(listOf())
    }

    fun searchStations() {
        _stations.value = Station.search(searchWord.value.orEmpty())
    }

    fun searchBuses() {
        stationSelected.value?.also { station ->
            val buses = Bus.search(station)
            _arrivalInfoes.value = buses.map { bus ->
                ArrivalInfo.search(station, bus)
            }
        }
    }
}