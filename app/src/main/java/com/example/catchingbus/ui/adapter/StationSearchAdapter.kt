package com.example.catchingbus.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.catchingbus.databinding.ItemStationPreviewBinding
import com.example.catchingbus.model.Station

class StationSearchAdapter : ListAdapter<Station, StationSearchViewHolder>(StationDiffcallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationSearchViewHolder {
        Log.d("problem","oncreateViewHolder")
        return StationSearchViewHolder(
            ItemStationPreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: StationSearchViewHolder, position: Int) {
        val station = currentList[position]
        Log.d("problem","station : ${currentList[position].name}")
        Log.d("problem","station의 크기 , ${currentList.size}")
        holder.bind(station)
    }

    companion object {
        private val StationDiffcallback = object : DiffUtil.ItemCallback<Station>(){
            //리사이클러뷰에서 데이터를 감지하는거 DiffUtil
            override fun areContentsTheSame(oldItem: Station, newItem: Station): Boolean {
                return oldItem == newItem
            }
            override fun areItemsTheSame(oldItem: Station, newItem: Station): Boolean {
                return oldItem.name==newItem.name
            }
        }
    }
}