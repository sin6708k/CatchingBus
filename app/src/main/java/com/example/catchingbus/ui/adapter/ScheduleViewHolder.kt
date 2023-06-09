package com.example.catchingbus.ui.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Schedule
import com.example.catchingbus.databinding.ItemScheduleBinding
import com.example.catchingbus.viewmodel.FavoriteViewModel

class ScheduleViewHolder(
    private val binding : ItemScheduleBinding,
) :RecyclerView.ViewHolder(binding.root) {

    private lateinit var favoriteViewModel: FavoriteViewModel
    fun bind(item: Schedule) {
        val start_time = item.startTime
        val end_time = item.endTime

        itemView.apply {
            binding.timeText.text = start_time.toString() + "~" + end_time.toString()
        }
        fun setItemClickListener(
            listener: FavoriteAdapter.OnFavoriteClickListener?,
            favorite: Favorite
        ) {

            itemView.setOnClickListener {
                Log.d("problem", "busSearchViewHolder : ${favorite}")
                listener?.onFavoriteClick(favorite)
            }

        }
    }
}
