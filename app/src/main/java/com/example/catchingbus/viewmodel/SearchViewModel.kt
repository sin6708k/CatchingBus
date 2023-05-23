package com.example.catchingbus.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchingbus.model.ArrivalInfo
import com.example.catchingbus.model.Bus
import com.example.catchingbus.model.Station
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel: ViewModel() {

    val searchWord: MutableLiveData<String> by lazy {
        MutableLiveData("")
    }

    val stations: LiveData<List<Station>> get() = _stations
    private val _stations: MutableLiveData<List<Station>> by lazy {
        MutableLiveData(listOf())
    }

    val selectedStation: MutableLiveData<Station?> = MutableLiveData(null)

    private var buses: List<Bus> = listOf()

    val arrivalInfoes: LiveData<List<ArrivalInfo>> get() = _arrivalInfoes
    private val _arrivalInfoes: MutableLiveData<List<ArrivalInfo>> by lazy {
        MutableLiveData(listOf())
    }

    init {
        selectedStation.observeForever { station ->
            viewModelScope.launch(Dispatchers.IO) {
                if (station != null) {
                    buses = Bus.search(station)
                }
            }
        }
    }

    /*
    fun searchStations() {
        viewModelScope.launch(Dispatchers.IO) {
            _stations.value = Station.search(searchWord.value.orEmpty())
        }
    }
     */
    fun searchStations() { //수정된 함수.
        viewModelScope.launch(Dispatchers.IO) {
            val searchResult = Station.search(searchWord.value.orEmpty())
            withContext(Dispatchers.Main) {
                _stations.value = searchResult
                Log.d("problem","검색결과 : ${searchResult}")
            }
        }
    }
    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            val station = selectedStation.value
            if (station != null) {
                _arrivalInfoes.value = buses.map { bus ->
                    ArrivalInfo.search(station, bus)
                }
            }
        }
    }
}