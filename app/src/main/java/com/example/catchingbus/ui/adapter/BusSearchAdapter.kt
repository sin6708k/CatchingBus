package com.example.catchingbus.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.catchingbus.databinding.ItemBusPreviewBinding
import com.example.catchingbus.model.Bus

class BusSearchAdapter : ListAdapter<Bus, BusSearchViewHolder>(BusDiffcallback){

    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): BusSearchViewHolder{
        return BusSearchViewHolder(
            ItemBusPreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }
    override fun onBindViewHolder(holder: BusSearchViewHolder, position:Int){
        val bus = currentList[position]
        holder.bind(bus) //itemview 구성
    }

    companion object {
        private val BusDiffcallback = object : DiffUtil.ItemCallback<Bus>(){ //데이터 변경 감지 하는 놈. 삭제 추가 식별
            override fun areContentsTheSame(oldItem: Bus, newItem: Bus): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Bus, newItem: Bus): Boolean {
                return oldItem.bus_num == newItem.bus_num
            }
        }
    }
}