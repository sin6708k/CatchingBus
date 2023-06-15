package com.example.catchingbus.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.catchingbus.R
import com.example.catchingbus.data.ArrivalChannelMessage
import com.example.catchingbus.ui.view.SplashActivity
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class ArrivalChannelObserver(private val notificationManager: NotificationManager) {

    private var job: Job? = null
    fun startObserving(arrivalChannelMessages: SharedFlow<ArrivalChannelMessage>) {
        Log.d("problem","옵저빙 시작")
        job?.cancel()
        job = CoroutineScope(Dispatchers.Default).launch {
            arrivalChannelMessages.collect { message ->
                sendNotification(message)
            }
        }
    }
    private fun sendNotification(message: ArrivalChannelMessage) {
        // 알림 생성 및 설정

        Log.d("problem","알림을 보낼거유\n ${message.arrivalInfo.toString()}")
        /*
        val alarmIntent = Intent(context, SplashActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            alarmIntent, //알림을 누를 시 이동되는 앱 화면
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("버스 알림!!!")
            .setContentText(message.arrivalInfo.station.name+"에"+message.arrivalInfo.bus.name+"오고 있습니다!")
            .setSmallIcon(R.drawable.ic_add_alarm)
            .setAutoCancel(true) //알림창 터치시 자동 삭제
            .setDefaults(NotificationCompat.DEFAULT_VIBRATE) //진동
            .setContentIntent(pendingIntent) //알림 터치시 pending intent로

        // 알림 표시
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
         */
        var builder: NotificationCompat.Builder? = null
        //푸시 알림을 보내기위해 시스템에 권한을 요청하여 생성
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //안드로이드 오레오 버전 대응
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            )
            builder = NotificationCompat.Builder(context, CHANNEL_ID)
        } else {
            builder = NotificationCompat.Builder(context)
        }
        //알림창 클릭 시 지정된 activity 화면으로 이동
        val alarmIntent = Intent(context, SplashActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            alarmIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )
        //알림창 제목
        builder.setContentTitle("버스 알림") //회의명노출
        builder.setContentText(message.arrivalInfo.station.name+"에\n"+message.arrivalInfo.bus.name+"가\n"+
                message.arrivalInfo.nowRemainingTimes+"남았습니다.")

        builder.setSmallIcon(R.mipmap.ic_bus)
        //.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_bus)) // 큰 아이콘 설정
        builder.setAutoCancel(true) //알림창 터치시 자동 삭제
        builder.setContentIntent(pendingIntent) //알림 터치시 pending intent로
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE)

        val notification = builder.build()//푸시알림 빌드
        manager.notify(1, notification) //NotificationManager를 이용하여 푸시 알림 보내기
    }



    companion object {
        // 알림 채널 ID와 알림 ID 등 필요한 상수들을 정의할 수 있습니다.
        private const val CHANNEL_ID = "arrival_channel"
        private const val NOTIFICATION_ID = 1
        private val CHANNEL_NAME = "My Channel"
    }
}