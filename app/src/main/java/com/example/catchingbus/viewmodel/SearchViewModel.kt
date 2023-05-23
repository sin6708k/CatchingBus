package com.example.catchingbus.viewmodel

import android.util.Log
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

    /*
    fun searchStations() {
        viewModelScope.launch {
            _stations.value = Station.search(searchWord.value.orEmpty())
        }
    }

     */
    fun searchStations() {
        viewModelScope.launch {
            val searchResult = Station.search(searchWord.value.orEmpty())
            _stations.value = searchResult

            // 검색 결과가 초기화된 후에 필요한 추가 작업 수행
            if (!searchResult.isNullOrEmpty()) {
                // 추가 작업 수행 코드
                Log.d("problem","결과 : ${searchResult}")
            }
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