package com.example.catchingbus.ui.adapter

import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.catchingbus.R
import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.databinding.ItemBusPreviewBinding
import com.example.catchingbus.databinding.ItemFavoriteBinding
import com.example.catchingbus.viewmodel.FavoriteViewModel
import com.example.catchingbus.viewmodel.SearchViewModel
import kotlin.time.Duration

class FavoriteViewHolder(
    private val binding : ItemFavoriteBinding,
    private val listener: FavoriteAdapter.OnFavoriteClickListener?,
    private val onFavoriteRemoveClickListener: FavoriteAdapter.OnFavoriteRemoveClickListener?
) :RecyclerView.ViewHolder(binding.root) {

    private lateinit var favoriteViewModel : FavoriteViewModel
    fun bind(item : Favorite){
        val station_name: String = item.station?.name ?: ""
        val bus_num: String = item.bus?.name ?: ""

        itemView.apply{
            binding.StationTextView.text = station_name
            binding.BusTextView.text=bus_num
        }
        val imageView = itemView.findViewById<ImageView>(R.id.delete_favorite)
        imageView.setOnClickListener {
            onFavoriteRemoveClickListener?.onFavoriteRemoveClick(item)
        }
    }
    fun setItemClickListener(
        listener: FavoriteAdapter.OnFavoriteClickListener?,
        favorite: Favorite
    ) {

        itemView.setOnClickListener {
            Log.d("problem","busSearchViewHolder : ${favorite}")
            listener?.onFavoriteClick(favorite)
        }

    }
}
