package com.example.catchingbus.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catchingbus.databinding.ActivityAfterSearchBinding
import com.example.catchingbus.ui.adapter.BusSearchAdapter

class AfterSearchActivity : AppCompatActivity() {

    private val binding : ActivityAfterSearchBinding by lazy{
        ActivityAfterSearchBinding.inflate(layoutInflater)
    }

    private lateinit var busSearchAdapter: BusSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("problem","after _search")
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        SetupRecyclerView() //리사이클러뷰 구성하기
    }

    private fun SetupRecyclerView(){
        busSearchAdapter = BusSearchAdapter() //어뎁터 연결
        binding.busDetailLayout.apply { //리사이클러뷰 구성.
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter =  busSearchAdapter
        }
    }
}