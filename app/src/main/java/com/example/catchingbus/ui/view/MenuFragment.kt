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
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Station
import com.example.catchingbus.databinding.ActivityMainBinding
import com.example.catchingbus.databinding.FragmentMenuBinding
import com.example.catchingbus.databinding.FragmentSearchBinding
import com.example.catchingbus.ui.adapter.FavoriteAdapter
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
class MenuFragment : Fragment(), FavoriteAdapter.OnFavoriteClickListener {
    // TODO: Rename and change types of parameters

    private var _binding: FragmentMenuBinding? = null
    private val binding: FragmentMenuBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var sharedViewModel : SearchViewModel
    private lateinit var favoriteViewModel : FavoriteViewModel
    private lateinit var FavoriteAdapter: FavoriteAdapter
    private lateinit  var onFavoriteRemoveClickListener: FavoriteAdapter.OnFavoriteRemoveClickListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        favoriteViewModel = ViewModelProvider(requireActivity()).get(FavoriteViewModel::class.java)
        mainBinding = mainActivity.binding
        setupRecyclerView()
        //FavoriteAdapter.setOnFavoriteClickListener(this)
        favoriteViewModel.favorites.observe(viewLifecycleOwner) { newFavorite ->
            //Log.d("problem","값 변화 : ${newStations[0]}")
            FavoriteAdapter.submitList(newFavorite)
        }
        return binding.root
    }


    private fun setupRecyclerView() { //리사이클러뷰 규성.
        Log.d("problem", "리사이클러뷰구성합니다.")
        FavoriteAdapter = FavoriteAdapter()
        binding.favoriteRecycler.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = FavoriteAdapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onFavoriteClick(favorite: Favorite) {  //즐겨찾기에서 x 누를때
        favoriteViewModel.removeFavorite(favorite)
    }
}