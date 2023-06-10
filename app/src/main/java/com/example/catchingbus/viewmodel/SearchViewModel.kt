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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.properties.Delegates.observable

class SearchViewModel: ViewModel() {
    companion object {
        const val TAG = "problem"
    }

    // View에서 검색 창에 입력할 때마다 이 field를 그 String으로 설정해야 한다
    val searchWord = MutableLiveData("")

    // 이 field의 값이 바뀔 때마다 View에서 보여주는 Station들을 갱신해야 한다
    val stations: LiveData<List<Station>> get() = _stations
    private val _stations = MutableLiveData<List<Station>>(emptyList())

    // View에서 Station을 선택할 때마다 이 field를 그 Station로 설정해야 한다
    val selectedStation = MutableLiveData<Station?>()

    // 이 field의 값이 바뀔 때마다 View에서 보여주는 BusContent들을 갱신해야 한다
    val busContents: LiveData<List<BusContent>> get() = _busContents
    private val _busContents = MutableLiveData<List<BusContent>>(emptyList())

    private var buses: List<Bus> by observable(listOf()) { _, _, _ ->
        updateArrivalInfoes()
    }
    private var arrivalInfoes: Map<Bus, ArrivalInfo> by observable(emptyMap()) { _, _, _ ->
        updateBusContents()
    }
    private var favorites: Map<Bus, Favorite> by observable(emptyMap()) { _, _, _ ->
        updateBusContents()
    }

    init {
        stations.observeForever {
            Log.d(TAG, it.joinToString("\n * ", "on stations.setValue()\n * "))
        }
        selectedStation.observeForever {
            searchBuses(it)
        }
        updateFavorites()
    }

    // View에서 검색 창에 입력을 마칠 때마다 이 function을 호출해야 한다
    fun searchStations() = viewModelScope.launch {
        _stations.value = StationService.search(searchWord.value.orEmpty())
    }

    private fun searchBuses(station: Station?) = viewModelScope.launch {
        buses = if (station != null) {
            BusService.search(station)
        } else {
            listOf()
        }
    }

    // View에서 새로고침 버튼을 누를 때마다 이 function을 호출해야 한다
    fun updateArrivalInfoes() = viewModelScope.launch {
        selectedStation.value?.let { station ->
            arrivalInfoes = buses.associateWith { bus ->
                ArrivalInfoService.search(station, bus)
            }
        }
    }

    // View에서 각 Bus의 즐겨찾기 버튼을 누를 때마다 이 function을 호출해야 한다
    /*
    fun addOrRemoveFavorite(busContent: BusContent) = viewModelScope.launch {
        selectedStation.value?.let { station ->
            if (busContent.favorite == null) {
                Log.d("problem","즐겨찾기 추가")
                FavoriteRepo.add(Favorite(station, busContent.bus))
            } else {
                Log.d("problem","즐겨찾기 삭제")
                FavoriteRepo.remove(busContent.favorite)
            }
        }
    }
     */
    /*
    fun addOrRemoveFavorite(bus: Bus) = viewModelScope.launch {
        selectedStation.value?.let { station ->
            favorites.value?.let { favorites ->
                favorites[bus].let {
                    if (it == null) {
                        val favorite = Favorite(station, bus)
                        Log.d("problem", "즐겨찾기 추가 : ${Favorite(station, bus)}")
                        FavoriteRepo.add(favorite)
                        _favorites.value = _favorites.value?.plus(favorite.bus to favorite)
                    } else {
                        Log.d("problem", "즐겨찾기 삭제 : ${Favorite(station, bus)}")
                        FavoriteRepo.remove(it)
                        @@ -135, 27+135, 4 @@ class SearchViewModel: ViewModel() {
                        }
                    }
                }
            }
        }
    }

     */

    private fun updateFavorites() = viewModelScope.launch {
        FavoriteRepo.data.collectLatest {
            favorites = it.associateBy { favorite ->
                favorite.bus
            }
        }
    }

    private fun updateBusContents() {
        _busContents.value = buses.map {
            BusContent(it, arrivalInfoes[it], favorites[it])
        }
    }
}