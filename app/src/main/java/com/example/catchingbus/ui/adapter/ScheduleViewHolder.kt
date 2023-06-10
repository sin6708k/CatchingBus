package com.example.catchingbus.ui.adapter

import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.catchingbus.R
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Schedule
import com.example.catchingbus.databinding.ItemScheduleBinding
import com.example.catchingbus.viewmodel.FavoriteViewModel

class ScheduleViewHolder(
    private val binding : ItemScheduleBinding,
    private val onScheduleRemoveClickListener: ScheduleAdapter.OnScheduleRemoveClickListener?
) :RecyclerView.ViewHolder(binding.root) {

    private lateinit var favoriteViewModel: FavoriteViewModel
    fun bind(item: Schedule) {
        Log.d("problem", "bind : ${item}")
        val start_time = item.startTime
        val end_time = item.endTime

        itemView.apply {
            binding.timeText.text = start_time.toString() + "~" + end_time.toString()
        }

        val imageView = itemView.findViewById<ImageView>(R.id.delete_time)
        imageView.setOnClickListener {
            onScheduleRemoveClickListener?.onScheduleRemoveClick(item)
        }
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
