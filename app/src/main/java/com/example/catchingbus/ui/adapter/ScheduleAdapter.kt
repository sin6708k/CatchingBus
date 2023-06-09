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


class ScheduleAdapter: ListAdapter<Schedule, ScheduleViewHolder>(ScheduleDiffcallback) {
    private lateinit var binding: ItemScheduleBinding
    private  lateinit var binding2: FragmentScheduleBinding
    private  lateinit var dialogBinding : PopupWindowBinding
    private var onFavoriteClickListener : FavoriteAdapter.OnFavoriteClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        binding = ItemScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding2 = FragmentScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        dialogBinding = PopupWindowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = getItem(position)
        holder.bind(schedule)
        Log.d("problem","스케쥴어뎁터")
        binding2.addAlarm.setOnClickListener {
            Log.d("problem","알람추가하기")

        }

    }
    /*
    fun showDialog(){

        var dialog = this.context?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(dialogBinding.root)
        dialog?.setCancelable(false)

        dialogBinding.finish.setOnClickListener{ //닫기버튼
            val result = dialogBinding.searchText.text.toString() //결과
            dialog?.dismiss()
        }
        dialogBinding.finish.setOnClickListener { //완료버튼
            dialog?.dismiss()
        }
        dialog?.show()
        dialogBinding.close
        dialog?.window?.setLayout(1000, 1000)
    }
     */


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
