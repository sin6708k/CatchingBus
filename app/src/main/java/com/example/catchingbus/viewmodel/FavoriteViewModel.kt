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
import com.example.catchingbus.model.ScheduleRepo
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime

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
    private val _schedules = MutableLiveData<List<Schedule>>(emptyList())

    init {
        favorites.observeForever {
            Log.d(TAG, it.joinToString("\n * ", "on favorites.setValue()\n * "))
        }
        selectedFavorite.observeForever {
            Log.d(TAG, "on selectedFavorite.setValue\n * $it")
            updateSchedules(it, ScheduleRepo.data.value)
        }
        schedules.observeForever {
            Log.d(TAG, it.joinToString("\n * ", "on schedules.setValue()\n * "))
        }
    }

    // View에서 Favorite 삭제 버튼을 누를 때마다 이 function을 호출해야 한다.
    fun removeFavorite(favorite: Favorite) = viewModelScope.launch {
        Log.d(TAG, "removeFavorite() start")

        FavoriteRepo.remove(favorite)

        Log.d(TAG, "removeFavorite() end")
    }

    // View에서 Schedule 등록 버튼을 누를 때마다 이 function을 호출해야 한다.
    fun addSchedule(startTime: LocalTime, endTime: LocalTime) = viewModelScope.launch {
        Log.d(TAG, "addSchedule() start")

        selectedFavorite.value?.let {
            Log.d(TAG, "addSchedule($startTime, $endTime)")
            val schedule = Schedule(it, startTime, endTime)
            ScheduleRepo.add(schedule)
            updateSchedules(it, ScheduleRepo.data.value)
        }
        Log.d(TAG, "addSchedule() end")
    }

    // View에서 Schedule 삭제 버튼을 누를 때마다 이 function을 호출해야 한다.
    fun removeSchedule(schedule: Schedule) = viewModelScope.launch {
        Log.d(TAG, "removeSchedule() start")

        selectedFavorite.value?.let {
            Log.d(TAG, "removeSchedule()")
            ScheduleRepo.remove(schedule)
            updateSchedules(it, ScheduleRepo.data.value)
        }
        Log.d(TAG, "removeSchedule() end")
    }

    private fun updateSchedules(favorite: Favorite?, allSchedules: List<Schedule>) {
        Log.d(TAG, "updateSchedules() start")

        _schedules.value = allSchedules.filter { schedule ->
            schedule.favorite == favorite
        }
        Log.d(TAG, "updateSchedules() end")
    }
}