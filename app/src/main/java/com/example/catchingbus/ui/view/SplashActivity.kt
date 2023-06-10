package com.example.catchingbus.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.catchingbus.R
import com.example.catchingbus.databinding.ActivitySplashBinding
import com.example.catchingbus.viewmodel.GlobalInitializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {

    private val splashScope = CoroutineScope(Dispatchers.Main)
    val binding: ActivitySplashBinding by lazy{
        ActivitySplashBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScope.launch {
            performTask()
            performNextTask()
        }

        setContentView(binding.root)
    }
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