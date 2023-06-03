package com.example.catchingbus.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Station
import com.example.catchingbus.model.ArrivalInfoService
import com.example.catchingbus.model.BusService
import com.example.catchingbus.model.FavoriteRepo
import com.example.catchingbus.model.StationService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    companion object {
        const val TAG = "problem"
    }

    // View에서 검색 창에 입력할 때마다 이 field를 그 String으로 설정해야 한다
    val searchWord = MutableLiveData("")

    // 이 field의 값이 바뀔 때마다 View에서 보여주는 Station들을 갱신해야 한다
    val stations: LiveData<List<Station>> get() = _stations
    private val _stations = MutableLiveData(listOf<Station>())

    // View에서 Station을 선택할 때마다 이 field를 그 Station로 설정해야 한다
    val selectedStation = MutableLiveData<Station?>()

    // 이 field의 값이 바뀔 때마다 View에서 보여주는 Bus들을 갱신해야 한다
    // 그러나 View에서 ArrivalInfo를 보여주는 것만으로 충분하면 하지 않아도 된다
    val buses: LiveData<List<Bus>> get() = _buses
    private val _buses = MutableLiveData(listOf<Bus>())

    // 이 field의 값이 바뀔 때마다 View에서 보여주는 ArrivalInfo들을 갱신해야 한다
    val arrivalInfoes: LiveData<List<ArrivalInfo>> get() = _arrivalInfoes
    private val _arrivalInfoes = MutableLiveData(listOf<ArrivalInfo>())

    // 이 field의 값이 바뀔 때마다 View에서 보여주는, 각 Bus에 해당하는 Favorite을 갱신해야 한다.
    val favorites: LiveData<Map<Bus, Favorite>> get() = _favorites
    private val _favorites = MutableLiveData(mapOf<Bus, Favorite>())

    init {
        viewModelScope.launch {
            FavoriteRepo.load()
        }
        stations.observeForever {
            Log.d(TAG, it.joinToString("\n * ", "on stations.setValue()\n * "))
        }
        selectedStation.observeForever {
            onSelectStation(it)
        }
        buses.observeForever {
            Log.d(TAG, it.joinToString("\n * ", "on buses.setValue()\n * "))
            refresh()
            onChangeBuses(it)
        }
        arrivalInfoes.observeForever {
            Log.d(TAG, it.joinToString("\n * ", "on arrivalInfoes.setValue()\n * "))
        }
        favorites.observeForever {
            Log.d(TAG, it.toList().joinToString("\n * ", "on favorites.setValue()\n * "))
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCleared() {
        super.onCleared()
        GlobalScope.launch {
            FavoriteRepo.save()
        }
    }

    // View에서 검색 창에 입력을 마칠 때마다 이 function을 호출해야 한다
    fun searchStations() = viewModelScope.launch {
        _stations.value = StationService.search(searchWord.value.orEmpty())
    }

    private fun onSelectStation(station: Station?) = viewModelScope.launch {
        _buses.value = if (station != null) {
            BusService.search(station)
        } else {
            emptyList()
        }
    }

    // View에서 새로고침 버튼을 누를 때마다 이 function을 호출해야 한다
    fun refresh() = viewModelScope.launch {
        selectedStation.value?.let { station ->
            _arrivalInfoes.value = buses.value?.map { bus ->
                ArrivalInfoService.search(station, bus)
            } ?: emptyList()
        }
    }

    private fun onChangeBuses(buses: List<Bus>?) = viewModelScope.launch {
        _favorites.value = if (buses != null) {
            selectedStation.value?.let { station ->
                val filtered = FavoriteRepo.data.value.filter {
                    it.station == station && buses.contains(it.bus)
                }
                filtered.associateBy({it.bus}, {it})
            }
        } else {
            emptyMap()
        }
    }

    // View에서 각 Bus의 즐겨찾기 버튼을 누를 때마다 이 function을 호출해야 한다
    fun addOrRemoveFavorite(bus: Bus) = viewModelScope.launch {
        selectedStation.value?.let { station ->
            favorites.value?.let { favorites ->
                favorites[bus].let {
                    if (it == null) {
                        val favorite = Favorite(station, bus)
                        FavoriteRepo.add(favorite)
                    } else {
                        FavoriteRepo.remove(it)
                    }
                }
            }
        }
    }
}