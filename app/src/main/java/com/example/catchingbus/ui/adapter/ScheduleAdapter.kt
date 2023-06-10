package com.example.catchingbus.ui.adapter

import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.catchingbus.R
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Schedule
import com.example.catchingbus.databinding.ActivityMainBinding
import com.example.catchingbus.databinding.FragmentScheduleBinding
import com.example.catchingbus.databinding.ItemScheduleBinding
import com.example.catchingbus.databinding.PopupWindowBinding


class ScheduleAdapter : ListAdapter<Schedule, ScheduleViewHolder>(ScheduleDiffcallback) {

    private  var onScheduleRemoveClickListener : ScheduleAdapter.OnScheduleRemoveClickListener?=null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleViewHolder(binding,onScheduleRemoveClickListener)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = getItem(position)
        Log.d("problem","스케줄 어뎁터  : 값변화 ${schedule}")
        holder.bind(schedule)
        val imageView = holder.itemView.findViewById<ImageView>(R.id.delete_time)
        imageView.setOnClickListener{
            Log.d("problem","스케쥴 제거하기")
            onScheduleRemoveClickListener?.onScheduleRemoveClick(schedule)
            Log.d("problem","스케줄은 ${schedule}")
        }
    }
    fun setRemoveScheduleClickListener(listener: OnScheduleRemoveClickListener){
        onScheduleRemoveClickListener=listener
    }
    interface OnScheduleRemoveClickListener {
        fun onScheduleRemoveClick(schedule: Schedule)
    }
    companion object {
        private val ScheduleDiffcallback = object : DiffUtil.ItemCallback<Schedule>() {
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.startTime == newItem.startTime && oldItem.endTime == newItem.endTime
            }
        }
    }
}
