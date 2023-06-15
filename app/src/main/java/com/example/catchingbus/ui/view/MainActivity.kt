package com.example.catchingbus.ui.view

import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.catchingbus.R
import com.example.catchingbus.databinding.ActivityMainBinding
import com.example.catchingbus.viewmodel.FavoriteViewModel
import com.example.catchingbus.viewmodel.SearchViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    //private lateinit var busSearchViewModel: SearchViewModel
    val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val sharedViewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }
    private lateinit var favoriteViewModel : FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setBottomNavigationView()
        if(savedInstanceState==null){ //앱이 처음 실행된 상태라면 구글맵이 포함된 화면을 보여줄거임.
            binding.bottomNavigationView.selectedItemId=R.id.fragment_home
        }
        binding.searchText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // 포커스를 얻었을 때의 동작을 여기에 작성합니다.
                // 예시: 클릭되었을 때의 동작
                Toast.makeText(this, "EditText 클릭됨", Toast.LENGTH_SHORT).show()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                    SearchFragment()
                ).commit()
            }
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favoriteViewModel.initialize(notificationManager)
    }
    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_layout)

        if (currentFragment is AfterSearchFragment) {
            // 이전 프래그먼트로 이동
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
    private fun setBottomNavigationView(){ //바텀 네비게이션 작업. 아이콘 누르면 화면 이동.
        binding.bottomNavigationView.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.fragment_home->{ //홈버튼 누를시.
                    Toast.makeText(this,"홈버튼을 눌렀습니다",Toast.LENGTH_SHORT).show()
                    binding.textLayout.visibility=View.VISIBLE
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        HomeFragment()
                    )
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.fragment_search->{ //검색버튼
                    Toast.makeText(this,"검색버튼을 눌렀습니다",Toast.LENGTH_SHORT).show()
                    binding.textLayout.visibility=View.VISIBLE
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        SearchFragment()
                    )
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.fragment_menu->{ //메뉴버튼
                    Toast.makeText(this,"메뉴튼을 눌렀습니다",Toast.LENGTH_SHORT).show()
                    binding.textLayout.visibility=View.GONE
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout,
                        MenuFragment()
                    )
                        .addToBackStack(null)
                        .commit()
                    true
                }
                else->false //다른버튼 누르면 안댐.
            }
        }
    }
}