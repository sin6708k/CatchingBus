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
import kotlinx.datetime.LocalTime

class FavoriteViewModel: ViewModel() {
    companion object {
        const val TAG = "problem"
    }

    val favorites: LiveData<List<Favorite>> = FavoriteRepo.data.asLiveData()

    val selectedFavorite = MutableLiveData<Favorite?>()

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

    fun removeFavorite(favorite: Favorite) = viewModelScope.launch {
        FavoriteRepo.remove(favorite)
    }

    private fun onSelectFavorite(favorite: Favorite?) = viewModelScope.launch {
        _schedules.value = favorite?.alarmActiveSchedules
    }

    fun addSchedule(startTime: LocalTime, endTime: LocalTime) = viewModelScope.launch {
        selectedFavorite.value?.let {
            val schedule = Schedule(startTime, endTime)
            it.alarmActiveSchedules.add(schedule)
            FavoriteRepo.update(it)
            onSelectFavorite(it)
        }
    }

    fun removeSchedule(schedule: Schedule) = viewModelScope.launch {
        selectedFavorite.value?.let {
            it.alarmActiveSchedules.remove(schedule)
            FavoriteRepo.update(it)
            onSelectFavorite(it)
        }
    }
}