package com.example.catchingbus.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.catchingbus.data.Station
import com.example.catchingbus.databinding.ItemStationPreviewBinding


class StationSearchAdapter : ListAdapter<Station, StationSearchViewHolder>(StationDiffcallback){

    private var onItemClickListener : OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationSearchViewHolder {
        val binding = ItemStationPreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return StationSearchViewHolder(binding,onItemClickListener)
    }

    override fun onBindViewHolder(holder: StationSearchViewHolder, position: Int) {
        val station = getItem(position) //station은 list중에서 position을 반환
        holder.bind(station) //반환된 station을 연결함.
        holder.itemView.setOnClickListener {
            Log.d("problem","클릭클릭")
            onItemClickListener?.onItemClick(station)
        }
    }

    fun setOnItemClickListener(listener : OnItemClickListener){
        Log.d("problem","setOnItemClickListener")
        onItemClickListener = listener
    }

    interface OnItemClickListener{
        fun onItemClick(station: Station)
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