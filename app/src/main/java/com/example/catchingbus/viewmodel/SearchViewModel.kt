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

    // View에서 검색 창에 입력할 때마다 이 field를 그 String으로 설정해야 한다
    val searchWord by lazy { MutableLiveData("") }

    // 이 field의 값이 바뀔 때마다 View에서 보여주는 Station들을 갱신해야 한다
    val stations: LiveData<List<Station>> get() = _stations
    private val _stations by lazy { MutableLiveData(listOf<Station>()) }

    // View에서 Station을 선택할 때마다 이 field를 그 Station로 설정해야 한다
    val selectedStation: MutableLiveData<Station?> = MutableLiveData(null)

    // 이 field의 값이 바뀔 때마다 View에서 보여주는 Bus들을 갱신해야 한다
    // 그러나 View에서 ArrivalInfo를 보여주는 것만으로 충분하면 하지 않아도 된다
    val buses: LiveData<List<Bus>> get() = _buses
    private val _buses by lazy { MutableLiveData(listOf<Bus>()) }

    // 이 field의 값이 바뀔 때마다 View에서 보여주는 ArrivalInfo들을 갱신해야 한다
    val arrivalInfoes: LiveData<List<ArrivalInfo>> get() = _arrivalInfoes
    private val _arrivalInfoes by lazy { MutableLiveData(listOf<ArrivalInfo>()) }

    init {
        stations.observeForever {
            Log.d(TAG, it.joinToString("\n   ", "On stations.setValue()\n"))
        }
        selectedStation.observeForever {
            if (it != null) {
                onSelectStation(it)
            }
        }
        buses.observeForever {
            Log.d(TAG, it.joinToString("\n   ", "On buses.setValue()\n"))
            if (it != null) {
                refresh()
            }
        }
        arrivalInfoes.observeForever {
            Log.d(TAG, it.joinToString("\n   ", "On arrivalInfoes.setValue()\n"))
        }
    }

    // View에서 검색 창에 입력을 마칠 때마다 이 function을 호출해야 한다
    fun searchStations() = viewModelScope.launch {
        _stations.value = StationService.search(searchWord.value.orEmpty())
    }

    private fun onSelectStation(station: Station) = viewModelScope.launch {
        _buses.value = BusService.search(station)
    }

    // View에서 새로고침 버튼을 누를 때마다 이 function을 호출해야 한다
    fun refresh() = viewModelScope.launch {
        selectedStation.value?.let { station ->
            _arrivalInfoes.value = buses.value?.map { bus ->
                ArrivalInfoService.search(station, bus)
            } ?: listOf()
        }
    }
}