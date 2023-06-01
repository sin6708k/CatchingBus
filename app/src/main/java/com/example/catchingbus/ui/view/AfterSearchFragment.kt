package com.example.catchingbus.ui.view

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catchingbus.R
import com.example.catchingbus.databinding.FragmentAfterSearchBinding
import com.example.catchingbus.databinding.FragmentSearchBinding
import com.example.catchingbus.ui.adapter.BusSearchAdapter
import com.example.catchingbus.viewmodel.SearchViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AfterSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AfterSearchFragment : Fragment() {
    private var _binding: FragmentAfterSearchBinding? = null
    private val binding: FragmentAfterSearchBinding get() = _binding!!
    private val sharedViewModel: SearchViewModel by lazy {
        ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
    }
    private lateinit var busSearchAdapter: BusSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("problem","afterSearch 프래그먼트")
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAfterSearchBinding.inflate(inflater, container, false)
        SetupRecyclerView()

        sharedViewModel.arrivalInfoes.observe(viewLifecycleOwner) { arriveinfo ->
            Log.d("problem","값 변화 : ${arriveinfo}")
         busSearchAdapter.submitList(arriveinfo)
        }
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun SetupRecyclerView(){
        Log.d("problem","after search 리사이클러뷰 만들거")
        busSearchAdapter = BusSearchAdapter() //어뎁터 연결
        binding.busDetailLayout.apply { //리사이클러뷰 구성.
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter =  busSearchAdapter
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AfterSearch.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AfterSearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
