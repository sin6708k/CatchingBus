package com.example.catchingbus.ui.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.catchingbus.databinding.ItemStationPreviewBinding
import com.example.catchingbus.model.Station

class StationSearchViewHolder(
    private  val binding : ItemStationPreviewBinding
) :RecyclerView.ViewHolder(binding.root) {

    fun bind(station : Station){
        val name = station.name //정류장 이름
        val longitude = station.longitude //경도
        val latitude = station.latitude //위도
        val id = station.id //

        itemView.apply { //각각의 데이터와 구성요소를 연결함.
            binding.stationName.text=name
        }
    }

    fun setItemClickListener(
        listener: StationSearchAdapter.OnItemClickListener?,
        station: Station
    ) {
        itemView.setOnClickListener {
            listener?.onItemClick(station)
            Log.d("problem","station : ${station.name}")
            //여기서 station name을 넘겨줘야하는데.
        }
    }

}