package com.example.catchingbus.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.catchingbus.databinding.ItemBusPreviewBinding
import com.example.catchingbus.model.ArrivalInfo
import com.example.catchingbus.model.Bus

class BusSearchAdapter : ListAdapter<ArrivalInfo, BusSearchViewHolder>(BusDiffcallback){

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