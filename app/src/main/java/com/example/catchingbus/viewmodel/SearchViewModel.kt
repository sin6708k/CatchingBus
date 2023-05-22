package com.example.catchingbus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchingbus.model.ArrivalInfo
import com.example.catchingbus.model.ArrivalUpdater
import com.example.catchingbus.model.Bus
import com.example.catchingbus.model.Station
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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

    private var arrivalUpdaters: List<ArrivalUpdater> = listOf()
        set(new) {
            field = new
            arrivalUpdateJob = new.map {
                viewModelScope.launch {
                    it.start()
                }
            }
        }

    private var arrivalUpdateJob: List<Job> = listOf()
        set(new) {
            field.forEach {
                it.cancel()
            }
            field = new
        }

    fun searchStations() {
        _stations.value = Station.search(searchWord.value.orEmpty())
    }

    fun searchBuses() {
        stationSelected.value?.also { station ->
            val buses = Bus.search(station)

            arrivalUpdaters = buses.map { bus ->
                ArrivalUpdater(station, bus).apply {
                    onLatestListener = {
                        _arrivalInfoes.value = arrivalUpdaters.map {
                            it.latest
                        }
                    }
                }
            }
        }
    }
}