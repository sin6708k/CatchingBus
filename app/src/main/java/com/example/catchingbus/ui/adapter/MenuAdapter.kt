

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.catchingbus.databinding.ItemFavoriteBinding


class MenuAdapter(
    private val items: MutableList<Dummy>,
    private val onDeleteClick: (Dummy) -> Unit,
    private val onItemClick: (Dummy) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    //item_favorite.xml이 리사이클러뷰의 각 항목에 적용되게 된다.
    inner class MenuViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(dummy: Dummy) {
            //Dummy 데이터를 기반으로 textView를 수정
            binding.StationTextView.text = dummy.station
            binding.BusTextView.text = dummy.bus

            // 리사클러뷰 항목 클릭 리스너 추가
            binding.root.setOnClickListener { onItemClick(dummy) }
    
            //X 이미지뷰 클릭 리스너 추가
            binding.imageViewX.setOnClickListener { onDeleteClick(dummy) }
            
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
