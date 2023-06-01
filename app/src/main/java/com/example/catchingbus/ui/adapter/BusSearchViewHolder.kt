package com.example.catchingbus.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.databinding.ItemBusPreviewBinding

class BusSearchViewHolder(
    private val binding : ItemBusPreviewBinding
) :RecyclerView.ViewHolder(binding.root) {

    fun bind(bus : ArrivalInfo){
        val num  = bus.bus.name.toString()
        val first_arrive = bus.remainingTimes[0] //첫차 도착시간
        val second_arrive = bus.remainingTimes[1] //뒷차 도착시간
        //val velocity = bus.velocity.toString() //아마 속도의 빠르기가 들어갈거임.

        itemView.apply{
            binding.busNum.text = num
            binding.firstArrive.text = first_arrive.toString()
            binding.secondArrive.text= second_arrive.toString()
            // 여기는 velocity를 판단해서 원의 색깔을 유동적으로 넣어줘야 할듯? 지금은 임의로
            //binding.circle.setImageResource(R.drawable.ic_green_circle) 임의로 초록색을 넣었음.
        }
    }
}
