package com.example.catchingbus.ui.adapter

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.databinding.ItemBusPreviewBinding
import com.example.catchingbus.viewmodel.FavoriteViewModel
import com.example.catchingbus.viewmodel.SearchViewModel
import kotlin.time.Duration

class BusSearchViewHolder(
    private val binding : ItemBusPreviewBinding,
    private val listener: BusSearchAdapter.OnBusClickListener?,
    private val lifecycleOwner: LifecycleOwner
) :RecyclerView.ViewHolder(binding.root) {

    private lateinit var favoriteViewModel : FavoriteViewModel


    fun bind(bus : ArrivalInfo){
        val num  = bus.bus.name
        var first_arrive : Duration= Duration.ZERO
        var second_arrive :Duration=Duration.ZERO

        if(bus.remainingTimes.size==2) { //버스가 두 대일떄는 정상적으로 받음.
            first_arrive = bus.remainingTimes[0] //첫차 도착시간
            second_arrive = bus.remainingTimes[1] //뒷차 도착시간
        }
        else if(bus.remainingTimes.size==1) //남은 버스가 하나일때.
            first_arrive=bus.remainingTimes[0]
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
        itemView.apply{
            binding.busNum.text = num
            binding.firstArrive.text = first_arrive.toString()
            binding.secondArrive.text= second_arrive.toString()
            //binding.circle.setImageResource(R.drawable.ic_green_circle) 임의로 초록색을 넣었음.
        }
        /*
        binding.bookMark.setOnClickListener{// 상태에 따라 선택자 리소스 적용
            binding.bookMark.isSelected = !binding.bookMark.isSelected
            Log.d("problem","북마크클릭 , ${binding.busNum.text.toString()}")
        }
         */
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
