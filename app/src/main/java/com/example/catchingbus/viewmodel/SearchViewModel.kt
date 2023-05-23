package com.example.catchingbus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchingbus.model.ArrivalInfo
import com.example.catchingbus.model.Bus
import com.example.catchingbus.model.Station
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {

    val searchWord by lazy { MutableLiveData("") }

    val stations: LiveData<List<Station>> get() = _stations
    private val _stations by lazy { MutableLiveData(listOf<Station>()) }

    val selectedStation: MutableLiveData<Station?> = MutableLiveData(null)
    private var buses = listOf<Bus>()

    val arrivalInfoes: LiveData<List<ArrivalInfo>> get() = _arrivalInfoes
    private val _arrivalInfoes by lazy { MutableLiveData(listOf<ArrivalInfo>()) }

    init {
        selectedStation.observeForever { station ->
            if (station != null) {
                onSelectStation(station)
            }
        }
    }

    fun searchStations() = viewModelScope.launch {
        _stations.value = Station.search(searchWord.value.orEmpty())
    }

    private fun onSelectStation(station: Station) = viewModelScope.launch {
        buses = Bus.search(station)
    }

    fun refresh() = viewModelScope.launch {
        val station = selectedStation.value
        if (station != null) {
            _arrivalInfoes.value = buses.map { bus ->
                ArrivalInfo.search(station, bus)
            }
        }
    }
}