package com.example.catchingbus.data

import androidx.lifecycle.MutableLiveData
import java.time.LocalDateTime
import java.util.Timer
import java.util.TimerTask
import kotlin.time.Duration
import kotlinx.datetime.plus
import kotlin.time.Duration.Companion.seconds


data class ArrivalInfo(
    val station: Station,
    val bus: Bus,
    val remainingTimes : List<Duration>
    //val remainingTimes: MutableLiveData<List<Duration>>,
) {
    val creationTime = LocalDateTime.now()
    val velocity: Velocity by lazy {
        Velocity.UNDETERMINED
    }
}
/*
    private var timer: Timer? = null

    fun startUpdatingRemainingTimes() {
        // 이미 업데이트가 진행 중인 경우 중복 실행 방지
        if (timer != null) return

        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                // 현재 remainingTimes 값을 가져옴
                val currentTimes = remainingTimes.value ?: return

                // remainingTimes 값을 1초씩 감소시킴
                val updatedTimes = currentTimes.map { it - 1.seconds }

                // UI 스레드에서 화면에 적용할 수 있도록 처리
                remainingTimes.postValue(updatedTimes)
            }
        }, 0, 1000) // 1초마다 업데이트
    }

    // ...
}

 */