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

    val searchWord: MutableLiveData<String> by lazy {
        MutableLiveData("")
    }

    val stations: LiveData<List<Station>> get() = _stations
    private val _stations: MutableLiveData<List<Station>> by lazy {
        MutableLiveData(listOf())
    }

    val selectedStation: MutableLiveData<Station?> by lazy {
        MutableLiveData(null)
    }

    private var buses: List<Bus> = listOf()

    val arrivalInfoes: LiveData<List<ArrivalInfo>> get() = _arrivalInfoes
    private val _arrivalInfoes: MutableLiveData<List<ArrivalInfo>> by lazy {
        MutableLiveData(listOf())
    }

    fun searchStations() {
        viewModelScope.launch {
            _stations.value = Station.search(searchWord.value.orEmpty())
        }
    }

    fun searchBuses() {
        viewModelScope.launch {
            selectedStation.value?.also { station ->
                buses = Bus.search(station)
            }
        }
    }

    fun searchArrivalInfoes() {
        viewModelScope.launch {
            selectedStation.value?.also { station ->
                _arrivalInfoes.value = buses.map { bus ->
                    ArrivalInfo.search(station, bus)
                }
            }
        }
    }
}