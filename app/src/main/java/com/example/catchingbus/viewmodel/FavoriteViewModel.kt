package com.example.catchingbus.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Schedule
import com.example.catchingbus.model.FavoriteRepo
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
//import kotlinx.datetime.LocalTime
import java.time.LocalTime

class FavoriteViewModel: ViewModel() {
    companion object {
        const val TAG = "problem"
    }

    // 이 field의 값이 바뀔 때마다 View에서 보여주는 Favorite들을 갱신해야 한다.
    val favorites: LiveData<List<Favorite>> = FavoriteRepo.data.asLiveData()

    // View에서 Favorite를 선택할 때마다 이 field를 그 Favorite로 설정해야 한다.
    val selectedFavorite = MutableLiveData<Favorite?>()

    // 이 field의 값이 바뀔 때마다 View에서 보여주는 Schedule들을 갱신해야 한다.
    val schedules: LiveData<List<Schedule>> get() = _schedules
    private val _schedules = MutableLiveData(listOf<Schedule>())

    init {
        viewModelScope.launch {
            FavoriteRepo.load()
        }
        favorites.observeForever {
            Log.d(TAG, it.joinToString("\n   ", "on favorites.setValue()\n"))
        }
        selectedFavorite.observeForever {
            onSelectFavorite(it)
        }
        schedules.observeForever {
            Log.d(TAG, it.joinToString("\n   ", "on schedules.setValue()\n"))
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCleared() {
        super.onCleared()
        GlobalScope.launch {
            FavoriteRepo.save()
        }
    }

    // View에서 Favorite 삭제 버튼을 누를 때마다 이 function을 호출해야 한다.
    fun removeFavorite(favorite: Favorite) = viewModelScope.launch {
        FavoriteRepo.remove(favorite)
    }

    private fun onSelectFavorite(favorite: Favorite?) = viewModelScope.launch {
        _schedules.value = favorite?.alarmActiveSchedules
    }

    // View에서 Schedule 등록 버튼을 누를 때마다 이 function을 호출해야 한다.
    fun addSchedule(startTime: LocalTime, endTime: LocalTime) = viewModelScope.launch {
        selectedFavorite.value?.let {
            val schedule = Schedule(startTime, endTime)
            it.alarmActiveSchedules.add(schedule)
            FavoriteRepo.update(it)
            onSelectFavorite(it)
        }
    }

    // View에서 Schedule 삭제 버튼을 누를 때마다 이 function을 호출해야 한다.
    fun removeSchedule(schedule: Schedule) = viewModelScope.launch {
        selectedFavorite.value?.let {
            it.alarmActiveSchedules.remove(schedule)
            FavoriteRepo.update(it)
            onSelectFavorite(it)
        }
    }
}