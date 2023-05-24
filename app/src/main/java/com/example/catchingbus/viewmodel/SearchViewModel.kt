package com.example.catchingbus.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Station
import com.example.catchingbus.model.ArrivalInfoService
import com.example.catchingbus.model.BusService
import com.example.catchingbus.model.StationService
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    companion object {
        const val TAG = "problem"
    }

    val searchWord by lazy { MutableLiveData("") }

    val stations: LiveData<List<Station>> get() = _stations
    private val _stations by lazy { MutableLiveData(listOf<Station>()) }

    val selectedStation: MutableLiveData<Station?> = MutableLiveData(null)
    private var buses = listOf<Bus>()

    val arrivalInfoes: LiveData<List<ArrivalInfo>> get() = _arrivalInfoes
    private val _arrivalInfoes by lazy { MutableLiveData(listOf<ArrivalInfo>()) }

    init {
        selectedStation.observeForever {
            if (it != null) {
                onSelectStation(it)
            }
        }
    }

    fun searchStations() = viewModelScope.launch {
        val stations = StationService.search(searchWord.value.orEmpty())
        _stations.value = stations

        val message = stations.joinToString("\n", "searchStations")
        Log.d(TAG, message)
    }

    private fun onSelectStation(station: Station) = viewModelScope.launch {
        buses = BusService.search(station)

        val message = buses.joinToString("\n", "onSelectStation\n")
        Log.d(TAG, message)
    }

    fun refresh() = viewModelScope.launch {
        val station = selectedStation.value
        if (station != null) {
            val arrivalInfoes = buses.map {
                ArrivalInfoService.search(station, it)
            }
            _arrivalInfoes.value = arrivalInfoes

            val message = arrivalInfoes.joinToString("\n", "refresh\n")
            Log.d(TAG, message)
        }
    }
}