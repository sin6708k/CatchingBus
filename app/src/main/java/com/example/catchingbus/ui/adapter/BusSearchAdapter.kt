package com.example.catchingbus.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.databinding.ItemBusPreviewBinding
import com.example.catchingbus.databinding.ItemStationPreviewBinding

class BusSearchAdapter : ListAdapter<ArrivalInfo, BusSearchViewHolder>(BusDiffcallback){

    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): BusSearchViewHolder{
        Log.d("problem","bussearch 어뎁터야")
        val binding = ItemBusPreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BusSearchViewHolder(binding)

    }
    override fun onBindViewHolder(holder: BusSearchViewHolder, position:Int){
        val Arrive= getItem(position) //station은 list중에서 position을 반환
        Log.d("problem","${Arrive} : ${position}")
        holder.bind(Arrive) //반환된 station을 연결함.

    }

    companion object {
        private val BusDiffcallback = object : DiffUtil.ItemCallback<ArrivalInfo>(){ //데이터 변경 감지 하는 놈. 삭제 추가 식별
            override fun areContentsTheSame(oldItem: ArrivalInfo, newItem: ArrivalInfo): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: ArrivalInfo, newItem: ArrivalInfo): Boolean {
                return oldItem.bus == newItem.bus
            }
        }
    }
}