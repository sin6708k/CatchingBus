package com.example.catchingbus.ui.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.databinding.ItemBusPreviewBinding
import com.example.catchingbus.viewmodel.SearchViewModel
import kotlin.time.Duration

class BusSearchViewHolder(
    private val binding : ItemBusPreviewBinding,
    private val listener: BusSearchAdapter.OnBusClickListener?
) :RecyclerView.ViewHolder(binding.root) {

    private lateinit var stationSearchViewModel: SearchViewModel



    fun bind(bus : ArrivalInfo){
        val num  = bus.bus.name.toString()
        if(bus.remainingTimes.size==2) {
            val first_arrive = bus.remainingTimes[0] //첫차 도착시간
            val second_arrive = bus.remainingTimes[1] //뒷차 도착시간
            itemView.apply{
                binding.busNum.text = num
                binding.firstArrive.text = first_arrive.toString()
                binding.secondArrive.text= second_arrive.toString()
                //binding.circle.setImageResource(R.drawable.ic_green_circle) 임의로 초록색을 넣었음.
            }
        }
        else{ //size가 1일대.
            val first_arrive=bus.remainingTimes[0]
            val second_arrive = ""
            itemView.apply{
                binding.busNum.text = num
                binding.firstArrive.text = first_arrive.toString()
                binding.secondArrive.text= second_arrive.toString()
                //binding.circle.setImageResource(R.drawable.ic_green_circle) 임의로 초록색을 넣었음.
            }
        }
        //val velocity = bus.velocity.toString() //아마 속도의 빠르기가 들어갈거임.
    }
    fun setItemClickListener(
        listener: BusSearchAdapter.OnBusClickListener?,
        arrivalInfo: ArrivalInfo
    ) {
        itemView.setOnClickListener {
            listener?.onBusClick(arrivalInfo)
        }
    }
}
