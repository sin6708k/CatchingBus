package com.example.catchingbus.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.catchingbus.R
import com.example.catchingbus.databinding.ItemBusPreviewBinding
import com.example.catchingbus.model.Bus

class BusSearchViewHolder(
    private val binding : ItemBusPreviewBinding
) :RecyclerView.ViewHolder(binding.root) {

    fun bind(bus : Bus){
        val num  = bus.bus_num.toString() //버스넘버
        val first_arrive = bus.first_arrive.toInt() //첫차 도착시간
        val second_arrive = bus.second_arrive.toInt() //뒷차 도착시간
        val velocity = bus.velocity.toInt() //아마 속도의 빠르기가 들어갈거임.

        itemView.apply{
            binding.busNum.text = num
            binding.firstArrive.text = first_arrive.toString()
            binding.secondArrive.text= second_arrive.toString()
            // 여기는 velocity를 판단해서 원의 색깔을 유동적으로 넣어줘야 할듯? 지금은 임의로
            //binding.circle.setImageResource(R.drawable.ic_green_circle) 임의로 초록색을 넣었음.
        }
    }
}
