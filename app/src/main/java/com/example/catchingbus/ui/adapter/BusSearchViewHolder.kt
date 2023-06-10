package com.example.catchingbus.ui.adapter

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.databinding.ItemBusPreviewBinding
import com.example.catchingbus.viewmodel.BusContent
import com.example.catchingbus.viewmodel.FavoriteViewModel
import com.example.catchingbus.viewmodel.SearchViewModel
import kotlin.time.Duration

class BusSearchViewHolder(
    private val binding : ItemBusPreviewBinding,
    private val listener: BusSearchAdapter.OnBusClickListener?,
    private val lifecycleOwner: LifecycleOwner
) :RecyclerView.ViewHolder(binding.root) {

    private lateinit var favoriteViewModel : FavoriteViewModel


    fun bind(bus : BusContent){
        val num  = bus.bus.name
        var first_arrive : Duration= Duration.ZERO
        var second_arrive :Duration=Duration.ZERO

        if(bus.arrivalInfo!!.remainingTimes.size==2) { //버스가 두 대일떄는 정상적으로 받음.
            first_arrive = bus.arrivalInfo!!.remainingTimes[0] //첫차 도착시간
            second_arrive = bus.arrivalInfo!!.remainingTimes[1] //뒷차 도착시간
        }
        else if(bus.arrivalInfo.remainingTimes.size==1) //남은 버스가 하나일때.
            first_arrive=bus.arrivalInfo!!.remainingTimes[0]
        //var first_arrive: Duration = Duration.ZERO
        //var second_arrive: Duration = Duration.ZERO

        /*
        bus.remainingTimes.observe(lifecycleOwner) { times ->
            if (times.size >= 2) {
                first_arrive = times[0]
                second_arrive = times[1]
            }
        }
         */
        if(bus.favorite==null){
            binding.bookMark.isSelected =false
        }
        else
            binding.bookMark.isSelected=true
        itemView.apply{
            binding.busNum.text = num
            binding.firstArrive.text = first_arrive.toString()
            binding.secondArrive.text= second_arrive.toString()
        }
        if (first_arrive == Duration.ZERO)  //초기화한값 그대로라면, 시간이 없으므로, 버스 없음을 출력.
            binding.firstArrive.text="버스 없음"
        if (second_arrive == Duration.ZERO)
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
