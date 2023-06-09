package com.example.catchingbus.ui.adapter

import android.app.Dialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Schedule
import com.example.catchingbus.databinding.ActivityMainBinding
import com.example.catchingbus.databinding.FragmentScheduleBinding
import com.example.catchingbus.databinding.ItemScheduleBinding
import com.example.catchingbus.databinding.PopupWindowBinding


class ScheduleAdapter : ListAdapter<Schedule, ScheduleViewHolder>(ScheduleDiffcallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("problem","스케쥴 어뎁터야")
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = getItem(position)
        Log.d("problem","스케줄 어뎁터  : 값변화 ${schedule}")
        holder.bind(schedule)
    }
    fun submitScheduleList(scheduleList: List<Schedule>) {
        submitList(scheduleList)
        Log.d("problem","서브밋 스케쥴, ${scheduleList}")
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
