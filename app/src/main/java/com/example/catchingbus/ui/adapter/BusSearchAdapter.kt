package com.example.catchingbus.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.catchingbus.R
import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.data.Bus
import com.example.catchingbus.data.Station
import com.example.catchingbus.databinding.ItemBusPreviewBinding
import com.example.catchingbus.databinding.ItemStationPreviewBinding
import com.example.catchingbus.viewmodel.BusContent


class BusSearchAdapter(
    private val lifecycleOwner: LifecycleOwner
): ListAdapter<BusContent, BusSearchViewHolder>(BusDiffcallback){

    private var onBusClickListener : OnBusClickListener? = null

    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): BusSearchViewHolder{
        val binding = ItemBusPreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return BusSearchViewHolder(binding,onBusClickListener,lifecycleOwner)
    }


    override fun onBindViewHolder(holder: BusSearchViewHolder, position:Int){
        val BusContent= getItem(position) //station은 list중에서 position을 반환
        Log.d("problem","${BusContent} : ${position}")
        holder.bind(BusContent) //반환된 station을 연결함.
        val imageView = holder.itemView.findViewById<ImageView>(R.id.book_mark)
        imageView.setOnClickListener{
            imageView.isSelected = !imageView.isSelected
            onBusClickListener?.onBusClick(BusContent)
        }
    }
    fun setOnBusClickListener(listener : BusSearchAdapter.OnBusClickListener){
        onBusClickListener = listener
    }
    interface OnBusClickListener{
        fun onBusClick(busContent: BusContent)
    }
    companion object {
        private val BusDiffcallback = object : DiffUtil.ItemCallback<BusContent>(){ //데이터 변경 감지 하는 놈. 삭제 추가 식별
            override fun areContentsTheSame(oldItem: BusContent, newItem: BusContent): Boolean {
                Log.d("jaehan","버스콜백")
                return oldItem.arrivalInfo?.nowRemainingTimes== newItem.arrivalInfo?.nowRemainingTimes
            }
            override fun areItemsTheSame(oldItem: BusContent, newItem: BusContent): Boolean {
                Log.d("jaehan","버스콜백12")
                return oldItem.arrivalInfo == newItem.arrivalInfo
            }
        }
    }
}