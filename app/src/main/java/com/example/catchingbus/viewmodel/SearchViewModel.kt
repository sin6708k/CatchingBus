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
import kotlin.concurrent.fixedRateTimer
import kotlin.properties.Delegates.observable

class SearchViewModel: ViewModel() {
    companion object {
        private const val TAG = "problem"
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

    private var _buses: List<Bus> by observable(listOf()) { _, _, new ->
        Log.d(TAG, new.joinToString("\n ", "on buses.setValue()\n "))
        updateArrivalInfoes()
    }
    private var _arrivalInfoes: Map<Bus, ArrivalInfo> by observable(emptyMap()) { _, _, new ->
        Log.d(TAG, new.toList().joinToString("\n ", "on arrivalInfoes.setValue()\n "))
        updateBusContents()
    }
    private var _favorites: Map<Bus, Favorite> by observable(emptyMap()) { _, _, new ->
        Log.d(TAG, new.toList().joinToString("\n ", "on favorites.setValue()\n "))
        updateBusContents()
    }

    init {
        stations.observeForever {
            Log.d(TAG, it.joinToString("\n ", "on stations.setValue()\n "))
        }
        selectedStation.observeForever {
            Log.d(TAG, "on selectedStation.setValue\n $it")
            searchBuses(it)
        }
        busContents.observeForever {
            Log.d(TAG, it.joinToString("\n ", "on busContents.setValue()\n "))
        }
        viewModelScope.launch {
            FavoriteRepo.data.collectLatest {
                Log.d(TAG, it.joinToString("\n ", "on FavoriteRepo.data.setValue()\n "))
                updateFavorites(it)
            }
        }
        fixedRateTimer(period = 1000) { // 1초마다
            refreshBusContents()
        }
    }

    // View에서 검색 창에 입력을 마칠 때마다 이 function을 호출해야 한다
    fun searchStations() = viewModelScope.launch {
        Log.d(TAG, "searchStations() start")

        _stations.value = StationService.search(searchWord.value.orEmpty())

        Log.d(TAG, "searchStations() end")
    }

    private fun searchBuses(station: Station?) = viewModelScope.launch {
        Log.d(TAG, "searchBuses() start")

        _buses = if (station != null) {
            BusService.search(station)
        } else {
            listOf()
        }
        Log.d(TAG, "searchBuses() end")
    }

    // View에서 새로고침 버튼을 누를 때마다 이 function을 호출해야 한다
    fun updateArrivalInfoes() = viewModelScope.launch {
        Log.d(TAG, "updateArrivalInfoes() start")

        selectedStation.value?.let { station ->
            _arrivalInfoes = _buses.associateWith { bus ->
                ArrivalInfoService.search(station, bus)
            }
        }
        Log.d(TAG, "updateArrivalInfoes() end")
    }

    // View에서 각 Bus의 즐겨찾기 버튼을 누를 때마다 이 function을 호출해야 한다
    fun addOrRemoveFavorite(busContent: BusContent) = viewModelScope.launch {
        Log.d(TAG, "addOrRemoveFavorite() start")

        selectedStation.value?.let { station ->
            Log.d("problem","${busContent}")
            if (busContent.favorite == null) {
                Log.d(TAG, "addOrRemoveFavorite() add")
                FavoriteRepo.add(Favorite(station, busContent.bus))
               // busContent.favorite = Favorite(station,busContent.bus) //내가 추가
                //favorites.values= listOf(Favorite(station,busContent.bus))
            } else {
                Log.d(TAG, "addOrRemoveFavorite() remove")
                FavoriteRepo.remove(busContent.favorite!!) //내가 추가
                //busContent.favorite=null
            }
        }
        Log.d(TAG, "addOrRemoveFavorite() end")
    }

    private fun updateFavorites(favorites: List<Favorite>) {
        Log.d(TAG, "updateFavorites() start")

        _favorites = favorites.associateBy { it.bus }

        Log.d(TAG, "updateFavorites() end")
    }

    private fun updateBusContents() {
        Log.d(TAG, "updateBusContents() start")

        _busContents.value = _buses.map {
            BusContent(it, _arrivalInfoes[it], _favorites[it])
        }
        Log.d(TAG, "updateBusContents() end")
    }

    private fun refreshBusContents() {
        Log.d(TAG, "refreshBusContents() start")

        _busContents.value = busContents.value

        Log.d(TAG, "refreshBusContents() end")
    }
}