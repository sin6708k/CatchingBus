package com.example.catchingbus.ui.view

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catchingbus.R
import com.example.catchingbus.databinding.ActivityMainBinding
import com.example.catchingbus.databinding.FragmentSearchBinding
import com.example.catchingbus.ui.adapter.BusSearchAdapter
import com.example.catchingbus.ui.adapter.StationSearchAdapter
import com.example.catchingbus.viewmodel.SearchViewModel
import com.google.android.material.textfield.TextInputEditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!
    private lateinit var  mainActivity: MainActivity
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var stationSearchViewModel: SearchViewModel

    //private lateinit var buSearchAdapter: BusSearchAdapter
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
        mainBinding = mainActivity.binding
        stationSearchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        search()
        setupRecyclerView()
        stationSearchViewModel.stations.observe(viewLifecycleOwner){ newStations->
            //Log.d("problem","값 변화 : ${newStations[0]}")
            stationSearchAdapter.submitList(newStations)
        }
        return binding.root
    }

    private fun search(){
        Log.d("problem","search를 합니다")
        mainBinding.searchText.setOnEditorActionListener { _, actionId, _, ->
            if(actionId==EditorInfo.IME_ACTION_DONE) {
                val searchText = mainBinding.searchText.text.toString()
                Log.d("problem","검색 텍스트는 ${searchText}")
                stationSearchViewModel.searchWord.value = searchText

                Log.d("problem","${stationSearchViewModel.searchWord.value.toString()}")
                //stationSearchViewModel.searchStations()
                //setupRecyclerView() //리사이클러뷰 만들기.
                stationSearchViewModel.searchStations()
                /*
                stationSearchViewModel.stations.observe(viewLifecycleOwner, Observer {
                    Log.d("problem","searchStations이 완료됩니다")
                    setupRecyclerView() // 리사이클러뷰 만들기.
                })

                 */
                //이렇게 하면 viewmodel에 스테이션이 들어가게됩니다.
                true
            }
            else{
                false
            }
         }
    }
    private fun setupRecyclerView(){ //리사이클러뷰 규성.
        Log.d("problem","리사이클러뷰구성합니다.")
        stationSearchAdapter = StationSearchAdapter()
        binding.stationRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter=stationSearchAdapter
        }
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}