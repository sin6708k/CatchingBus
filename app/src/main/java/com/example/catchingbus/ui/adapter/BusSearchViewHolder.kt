package com.example.catchingbus.ui.adapter

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.catchingbus.R
import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Velocity
import com.example.catchingbus.databinding.ItemBusPreviewBinding
import com.example.catchingbus.viewmodel.BusContent
import com.example.catchingbus.viewmodel.FavoriteViewModel
import com.example.catchingbus.viewmodel.SearchViewModel
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

class BusSearchViewHolder(
    private val binding : ItemBusPreviewBinding,
    private val listener: BusSearchAdapter.OnBusClickListener?,
    private val lifecycleOwner: LifecycleOwner
) :RecyclerView.ViewHolder(binding.root) {

    private lateinit var favoriteViewModel : FavoriteViewModel


    @OptIn(ExperimentalTime::class)
    fun bind(bus : BusContent){
        val num  = bus.bus.name
        var first_arrive : Duration= Duration.ZERO
        var second_arrive :Duration=Duration.ZERO
        var first_arrive_string =""
        var second_arrive_string=""
        val velocity = bus.arrivalInfo?.velocity


        if(bus.arrivalInfo!!.remainingTimes.size==2) { //버스가 두 대일떄는 정상적으로 받음.
            first_arrive=bus.arrivalInfo.nowRemainingTimes[0]
            second_arrive=bus.arrivalInfo.nowRemainingTimes[1]
            val (firstMinutes, firstSeconds) = first_arrive.toComponents { _, minutes, seconds, _ -> minutes to seconds }
            val (secondMinutes, secondSeconds) = second_arrive.toComponents { _, minutes, seconds, _ -> minutes to seconds }
            first_arrive_string = String.format("%02d:%02d", firstMinutes, firstSeconds)
            second_arrive_string = String.format("%02d:%02d", secondMinutes, secondSeconds)
            first_arrive_string = String.format("%02d:%02d", firstMinutes, firstSeconds)
            second_arrive_string = String.format("%02d:%02d", secondMinutes, secondSeconds)
        }
        else if(bus.arrivalInfo.remainingTimes.size==1) //남은 버스가 하나일때.
        {
            first_arrive=bus.arrivalInfo.nowRemainingTimes[0]
            val (firstMinutes, firstSeconds) = first_arrive.toComponents { _, minutes, seconds, _ -> minutes to seconds }
            first_arrive_string = String.format("%02d:%02d", firstMinutes, firstSeconds)
        }
        binding.bookMark.isSelected = bus.favorite != null

        itemView.apply{
            binding.busNum.text = num
            if(first_arrive< Duration.ZERO){
                binding.firstArrive.text="도착예정"
            }
            else
                binding.firstArrive.text = first_arrive_string
            if(second_arrive<Duration.ZERO){
                binding.secondArrive.text="도착예정"
            }
            else
                binding.secondArrive.text= second_arrive_string
            Log.d("ttttttt","${velocity.toString()},${bus.toString()}")
            when(velocity){

                Velocity.FAST -> {
                    binding.circle.setImageResource(R.drawable.ic_green_circle)
                }
                Velocity.SLOW ->{
                    binding.circle.setImageResource(R.drawable.ic_rec_circle)
                }
                else -> {
                    binding.circle.setImageResource(R.drawable.ic_yellow_circle)
                }
            }
        }
        if (first_arrive == Duration.ZERO)  //초기화한값 그대로라면, 시간이 없으므로, 버스 없음을 출력.
            binding.firstArrive.text="버스 없음"
        if (second_arrive ==Duration.ZERO)
            binding.secondArrive.text="버스 없음"
        //val velocity = bus.velocity.toString() //아마 속도의 빠르기가 들어갈거임.
    }
    fun setItemClickListener(
        listener: BusSearchAdapter.OnBusClickListener?,
        arrivalInfo: ArrivalInfo
    ) {
        /*
        itemView.setOnClickListener {
            Log.d("problem","busSearchViewHolder : ${arrivalInfo}")
            listener?.onBusClick(arrivalInfo)
        }
         */
    }
}
