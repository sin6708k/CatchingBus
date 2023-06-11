package com.example.catchingbus.model

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.catchingbus.R
import com.example.catchingbus.ui.view.SplashActivity

class NotificationService : BroadcastReceiver() {
    private val TAG = "MyNotificationReceiver"
    private val CHANNEL_ID = "my_channel_id"
    private val CHANNEL_NAME = "My Channel"

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("problem", "onReceive 알람이 들어옴!!")
        val contentValue = intent.getStringExtra("content")
        Log.d("problem", "onReceive contentValue값 확인 : $contentValue")

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
        builder.setContentTitle(contentValue) //회의명노출
        builder.setSmallIcon(R.mipmap.ic_bus) //알림창 아이콘
        builder.setAutoCancel(true) //알림창 터치시 자동 삭제
        builder.setContentIntent(pendingIntent) //알림 터치시 pending intent로
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE)
        val notification = builder.build()//푸시알림 빌드
        manager.notify(1, notification) //NotificationManager를 이용하여 푸시 알림 보내기
    }
}