package com.example.catchingbus.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.catchingbus.R
import com.example.catchingbus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setBottomNavigationView()
        if(savedInstanceState==null){ //앱이 처음 실행된 상태라면 구글맵이 포함된 화면을 보여줄거임.
            binding.bottomNavigationView.selectedItemId=R.id.fragment_home
        }
    }
    private fun setBottomNavigationView(){ //바텀 네비게이션 작업. 아이콘 누르면 화면 이동.
        binding.bottomNavigationView.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.fragment_home->{ //홈버튼 누를시.
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,HomeFragment()).commit()
                    true

                }
                R.id.fragment_search->{ //검색버튼
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,SearchFragment()).commit()
                        true
                }
                R.id.fragment_menu->{ //메뉴버튼
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,MenuFragment()).commit()
                    true
                }
                else->false //다른버튼 누르면 안댐.
            }
         }
    }
}