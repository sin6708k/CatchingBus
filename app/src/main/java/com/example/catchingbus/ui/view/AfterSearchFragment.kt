package com.example.catchingbus.ui.view

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catchingbus.R
import com.example.catchingbus.data.ArrivalInfo
import com.example.catchingbus.data.Favorite
import com.example.catchingbus.data.Station
import com.example.catchingbus.databinding.FragmentAfterSearchBinding
import com.example.catchingbus.databinding.FragmentSearchBinding
import com.example.catchingbus.ui.adapter.BusSearchAdapter
import com.example.catchingbus.ui.adapter.StationSearchAdapter
import com.example.catchingbus.viewmodel.BusContent
import com.example.catchingbus.viewmodel.FavoriteViewModel
import com.example.catchingbus.viewmodel.SearchViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AfterSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AfterSearchFragment : Fragment(),OnMapReadyCallback, BusSearchAdapter.OnBusClickListener {
    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null
    private lateinit var fusedLocationClient:FusedLocationProviderClient

    private var _binding: FragmentAfterSearchBinding? = null
    private val binding: FragmentAfterSearchBinding get() = _binding!!
    /*
    private val sharedViewModel: SearchViewModel by lazy {
        ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
    }
     */
    /*
    private val favoriteViewModel: FavoriteViewModel by lazy {
        ViewModelProvider(requireActivity()).get(FavoriteViewModel::class.java)
    }
     */
    private lateinit var sharedViewModel : SearchViewModel
    private lateinit var favoriteViewModel : FavoriteViewModel


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
        sharedViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        favoriteViewModel = ViewModelProvider(requireActivity()).get(FavoriteViewModel::class.java)
        SetupRecyclerView()
        busSearchAdapter.setOnBusClickListener(this)
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map->
            googleMap=map
            onMapReady(googleMap!!)
        }

        //arrivalinfoes를 관찰해서, 어뎁터에 넣어줌.
        sharedViewModel.busContents.observe(viewLifecycleOwner) { newBusContent ->
            Log.d("problem","값 변화 : ${newBusContent[0].arrivalInfo?.nowRemainingTimes}")
            busSearchAdapter.submitList(newBusContent)
            busSearchAdapter.notifyDataSetChanged()
        }
        return binding.root
    }

    override fun onBusClick(busContent: BusContent) { // 아이템뷰 클릭 이벤트 처리
        Log.d("problem","북마크 클릭!\n ${busContent}")
        sharedViewModel.addOrRemoveFavorite(busContent) //즐겨찾기 추가 혹은 있으면 제거
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 이전 프래그먼트로 이동
                parentFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
    private fun SetupRecyclerView(){
        Log.d("problem","after search 리사이클러뷰 만들거")
        busSearchAdapter = BusSearchAdapter(viewLifecycleOwner) //어뎁터 연결
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
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        Log.d("problem","이전버튼 애프터서치")
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onMapReady(p0: GoogleMap) {
        Log.d("problem","지도호출")
        googleMap = p0
        showCustomLocationOnMap()
    }
    @SuppressLint("MissingPermission")
    private fun showCustomLocationOnMap() {
        val latitude = sharedViewModel.selectedStation.value?.latitude // 지정할 위도 값
        val longitude = sharedViewModel.selectedStation.value?.longitude // 지정할 경도 값

        val customLatLng = LatLng(latitude!!, longitude!!)
        Log.d("problem","위도 : $latitude, 경도 : $longitude")

        val cameraPosition = CameraPosition.Builder()
            .target(customLatLng)
            .zoom(15f)
            .build()

        googleMap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        googleMap?.addMarker(MarkerOptions().position(customLatLng).title("지정한 위치"))
    }
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(callback: (Location?) -> Unit) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                callback(location) // 콜백 함수에 위치 정보 전달
            }
            .addOnFailureListener {
                    e -> Toast.makeText(requireContext(), "위치 정보를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show()
                callback(null) // 콜백 함수에 null 전달
            }
    }
}
