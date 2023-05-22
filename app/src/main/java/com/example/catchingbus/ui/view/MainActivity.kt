package com.example.catchingbus.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import com.example.catchingbus.R
import com.example.catchingbus.databinding.ActivityMainBinding
import com.example.catchingbus.viewmodel.SearchViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var busSearchViewModel: SearchViewModel
    val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Log.d("problem","oncreate")
        setBottomNavigationView()
        if(savedInstanceState==null){ //앱이 처음 실행된 상태라면 구글맵이 포함된 화면을 보여줄거임.
            binding.bottomNavigationView.selectedItemId=R.id.fragment_menu
        }
        //edit text -> viewmodel ->
    }
    private fun setBottomNavigationView(){ //바텀 네비게이션 작업. 아이콘 누르면 화면 이동.
        Log.d("problem","바텀네비게이션")
        binding.bottomNavigationView.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.fragment_home->{ //홈버튼 누를시.
                    Toast.makeText(this,"홈버튼을 눌렀습니다",Toast.LENGTH_SHORT).show()
                    Log.d("problem","홈버튼")
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        HomeFragment()
                    ).commit()
                    true
                }
                R.id.fragment_search->{ //검색버튼
                    Toast.makeText(this,"검색버튼을 눌렀습니다",Toast.LENGTH_SHORT).show()
                    Log.d("problem","검색버튼")
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        SearchFragment()
                    ).commit()
                        true
                }
                R.id.fragment_menu->{ //메뉴버튼
                    Toast.makeText(this,"메뉴튼을 눌렀습니다",Toast.LENGTH_SHORT).show()
                    Log.d("problem","메ㅙ버튼")
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        MenuFragment()
                    ).commit()
                    true
                }
                else->false //다른버튼 누르면 안댐.
            }
         }

    }
}