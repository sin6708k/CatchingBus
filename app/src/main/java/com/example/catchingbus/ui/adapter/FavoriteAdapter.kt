package com.example.catchingbus.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.catchingbus.R
import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Station
import com.example.catchingbus.databinding.ItemBusPreviewBinding
import com.example.catchingbus.databinding.ItemFavoriteBinding
import com.example.catchingbus.databinding.ItemStationPreviewBinding



class FavoriteAdapter: ListAdapter<Favorite, FavoriteViewHolder>(FavoriteDiffcallback){

    private var onFavoriteClickListener : OnFavoriteClickListener? = null
    private  var onFavoriteRemoveClickListener : OnFavoriteRemoveClickListener?=null
    override fun onCreateViewHolder(parent:ViewGroup, viewType: Int): FavoriteViewHolder{
        Log.d("problem","bussearch 어뎁터야")
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return FavoriteViewHolder(binding, onFavoriteClickListener, onFavoriteRemoveClickListener)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position:Int){
        val favorite = getItem(position)
        holder.bind(favorite) //반환된 station을 연결함.
        val imageView = holder.itemView.findViewById<ImageView>(R.id.delete_favorite)
        imageView.setOnClickListener{
            Log.d("problem","즐겨찾기 제거하기")
            //removeItem(position)
            onFavoriteRemoveClickListener?.onFavoriteRemoveClick(favorite)//
        }
        holder.itemView.setOnClickListener {
            onFavoriteClickListener?.onFavoriteClick(favorite)
        }
    }
    interface OnFavoriteRemoveClickListener {
        fun onFavoriteRemoveClick(favorite: Favorite)
    }
    fun removeItem(position: Int) {
        // 데이터셋에서 아이템 삭제
        val favorite = getItem(position)
        onFavoriteRemoveClickListener?.onFavoriteRemoveClick(favorite) //아이템 삭제하고 프래그먼트에도 말하기

        notifyItemRemoved(position) // 데이터셋을 업데이트하고 삭제된 아이템을 알림
    }
    fun setRemoveFavoriteClickListener(listener: OnFavoriteRemoveClickListener){
        onFavoriteRemoveClickListener=listener
    }
    fun setOnFavoriteClickListener(listener : OnFavoriteClickListener){
        Log.d("problem","setOnItemClickListener")
        onFavoriteClickListener = listener
    }
    interface OnFavoriteClickListener{
        fun onFavoriteClick(favorite: Favorite)
    }
    companion object {
        private val FavoriteDiffcallback = object : DiffUtil.ItemCallback<Favorite>(){ //데이터 변경 감지 하는 놈. 삭제 추가 식별
            override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem == newItem
            }
            override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
                return oldItem.bus==newItem.bus && oldItem.station == newItem.station
            }
        }
    }
}