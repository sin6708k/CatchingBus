package com.example.catchingbus.ui.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catchingbus.R
import com.example.catchingbus.data.Station
import com.example.catchingbus.databinding.ActivityMainBinding
import com.example.catchingbus.databinding.FragmentSearchBinding
import com.example.catchingbus.ui.adapter.StationSearchAdapter
import com.example.catchingbus.viewmodel.FavoriteViewModel
import com.example.catchingbus.viewmodel.SearchViewModel
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment(), StationSearchAdapter.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var sharedViewModel : SearchViewModel
    private lateinit var favoriteViewModel : FavoriteViewModel
    private lateinit var stationSearchAdapter: StationSearchAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        favoriteViewModel = ViewModelProvider(requireActivity()).get(FavoriteViewModel::class.java)
        mainBinding = mainActivity.binding
        // = ViewModelProvider(this).get(SearchViewModel::class.java)

        search()
        setupRecyclerView()
        stationSearchAdapter.setOnItemClickListener(this)
        sharedViewModel.stations.observe(viewLifecycleOwner) { newStations ->
            //Log.d("problem","값 변화 : ${newStations[0]}")
            stationSearchAdapter.submitList(newStations)
        }
        return binding.root
    }

    private fun search() {
        Log.d("problem", "search를 합니다")
        mainBinding.searchText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val searchText = mainBinding.searchText.text.toString()
                Log.d("problem", "검색 텍스트는 ${searchText}")
                sharedViewModel.searchWord.value = searchText

                Log.d("problem", "${sharedViewModel.searchWord.value.toString()}")
                sharedViewModel.searchStations()
                //이렇게 하면 viewmodel에 스테이션이 들어가게됩니다.
                true
            } else {
                Log.d("problem", "검색실패")
                false
            }
        }
    }

    private fun setupRecyclerView() { //리사이클러뷰 규성.
        Log.d("problem", "리사이클러뷰구성합니다.")
        stationSearchAdapter = StationSearchAdapter()
        binding.stationRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = stationSearchAdapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onItemClick(station: Station) {
        // 아이템뷰 클릭 이벤트 처리
        Log.d("problem", "아이템 클릭 , ${station.name}")
        mainBinding.searchText.setText(station.name)
        sharedViewModel.selectedStation.value = station
        sharedViewModel.busContents.observe(viewLifecycleOwner) { busContent ->
            if (busContent.isNotEmpty()) {
                // 값이 변경되었을 때만 프래그먼트 이동
                Log.d("problem","도착정보 값이 바껴서 이동할거야")
                val fragmentManager = requireActivity().supportFragmentManager
                fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, AfterSearchFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
        // CoroutineScope 생성
    }
}