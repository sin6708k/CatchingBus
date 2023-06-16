package com.example.catchingbus.ui.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.catchingbus.R
import com.example.catchingbus.databinding.ActivitySplashBinding
import com.example.catchingbus.viewmodel.GlobalInitializer
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SplashActivity : AppCompatActivity() {

    private val splashScope = CoroutineScope(Dispatchers.Main)
    val binding: ActivitySplashBinding by lazy{
        ActivitySplashBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScope.launch {
            val permissionGranted = performPermission()
            if (permissionGranted) {
                performTask()
                performNextTask()
            } else {
                // 권한이 허용되지 않았을 경우 처리할 내용
                //Toast.makeText(this@SplashActivity,"알림을 허용하지 않으면\n버스 알림을 받을 수 없습니다",Toast.LENGTH_SHORT).show()
                performTask()
                performNextTask()
            }
        }
        setContentView(binding.root)
    }
    private suspend fun performPermission(): Boolean = suspendCancellableCoroutine { continuation ->
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                continuation.resume(true)
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                continuation.resume(false)
            }
        }
        val builder = TedPermission.create()
            .setPermissionListener(permissionListener)
            //.setDeniedMessage("알림 권한을 거절하신다면 알림 기능을 사용할 수 없습니다")
            .setPermissions(Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.SCHEDULE_EXACT_ALARM, Manifest.permission.USE_EXACT_ALARM, Manifest.permission.RECEIVE_BOOT_COMPLETED, Manifest.permission.ACCESS_NOTIFICATION_POLICY)
            //.setPermissions(Manifest.permission.ACCESS_NOTIFICATION_POLICY)
        builder.check()
    }
    // 사용 예시

    private suspend fun performTask() {
        withContext(Dispatchers.IO) {
            GlobalInitializer.initialize(applicationContext.filesDir.absolutePath)
            delay(3000)

        }
    }

    private fun performNextTask() {
        Log.d("problem","로드 작업완료!!")
        val intent = Intent(this@SplashActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        splashScope.cancel() // 액티비티가 종료될 때 코루틴도 함께 취소
    }
}