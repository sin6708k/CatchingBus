

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catchingbus.R
import com.example.catchingbus.databinding.FragmentMenuBinding
import com.example.catchingbus.ui.view.FavoriteFragment
import java.io.Serializable


//테스트를 위한 더미 데이터입니다. station 이름, bus 번호, 알림시작시간(공백으로초기화), 알림종료시간(공백으로초기화)
data class Dummy(val station: String, val bus: String,
                 var StartTime: String = "",var EndTime:String="") : Serializable

//listOfItems = mutableList<Dummy>
private val listOfItems = mutableListOf(
    Dummy("경북대학교정문앞", "503"),
    Dummy("경북대학교북문앞", "706"),
    Dummy("경북대학교북문앞", "123"),
    Dummy("경북대학교북문앞", "124"),
    Dummy("경북대학교북문앞", "125")
)

//리사이클러 뷰 항목간 간격을 위한 클래스.
class TopSpacingItemDecoration(private val padding: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = padding
    }
}
//리사이클러 뷰 항목간 간격을 위한 클래스.



class MenuFragment : Fragment() {
    //MenuAdapter는 리사이클러뷰와 연결되는 어댑터
    private lateinit var adapter: MenuAdapter



    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)






        //리사이클러뷰와 어댑터를 연결합니다. 인자로는 mutablelist<Dummy>, 항목클릭시이벤트, x아이콘클릭시 이벤트
        adapter = MenuAdapter(listOfItems,onItemClick = { dummy ->
            navigateToFavoriteFragment(dummy)
        },onDeleteClick = { dummy ->
            deleteItem(dummy)
        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val topSpacingItemDecoration = TopSpacingItemDecoration(10)
        binding.recyclerView.addItemDecoration(topSpacingItemDecoration)
        //리사이클러뷰와 어댑터를 연결합니다. 인자로는 mutablelist<Dummy>, 항목클릭시이벤트, x아이콘클릭시 이벤트

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    //리사이클러뷰 항목 클릭시 발동될 이벤트 화면을 FavoriteFragment로 바꿈
    //dummy의 형식을 변환하고 싶다면 Dummy를 수정해야하며 MenuAdapter에서도 수정해야한다.
    private fun navigateToFavoriteFragment(dummy: Dummy) {
        // FavoriteFragment 인스턴스를 생성합니다.
        val favoriteFragment = FavoriteFragment()

        // Bundle을 생성하고 Dummy 객체를 추가합니다.
        val bundle = Bundle()
        bundle.putSerializable("dummy", dummy)

        // Bundle을 FavoriteFragment에 추가합니다.
        favoriteFragment.arguments = bundle

        // FragmentManager를 사용하여 FavoriteFragment로 이동합니다.
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame_layout, favoriteFragment)
            ?.addToBackStack(null)
            ?.commit()
    }
    //리사이클러뷰 항목 클릭시 이벤트 화면을 FavoriteFragment로 바꿈


    //리사이클러뷰 항목의 x 아이콘을 클릭시 발동되는 이벤트 해당 항목에 바인딩된 요소를 삭제
    //dummy의 형식을 변환하고 싶다면 Dummy를 수정해야하며 MenuAdapter에서도 수정해야한다.
    private fun deleteItem(dummy: Dummy) {
        // Remove item from list and notify adapter
        listOfItems.remove(dummy)
        adapter.notifyDataSetChanged()
    }
    //리사이클러뷰 항목의 x 아이콘을 클릭시 발동되는 이벤트 해당 항목에 바인딩된 요소를 삭제


}
