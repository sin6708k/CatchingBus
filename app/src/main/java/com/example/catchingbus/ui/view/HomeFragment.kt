package com.example.catchingbus.ui.view

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import com.example.catchingbus.R
import android.Manifest
import android.annotation.SuppressLint
import androidx.core.app.ActivityCompat
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import android.print.PrintAttributes.Margins
import android.util.Log
import android.widget.Toast
import com.example.catchingbus.databinding.FragmentHomeBinding
import com.example.catchingbus.databinding.FragmentSearchBinding
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
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(),OnMapReadyCallback {
    // TODO: Rename and change types of parameters

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null
    private lateinit var fusedLocationClient:FusedLocationProviderClient
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    companion object{
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_home,container,false)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        mapView = binding.homeMapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map->
            googleMap=map
            onMapReady(googleMap!!)
        }
        return view
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
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
            //getCurrentLocation()
//        checkLocationPermission() //위치권환 확인
    //showCurrentLocationOnMap() //
        showCustomLocationOnMap()
    }
    private fun checkLocationPermission(){
        // 현재 위치 표시를 위한 권한 확인
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // 위치 권한이 있는경우
           // showCurrentLocationOnMap()
        } else {
            // 위치 권한이 없는 경우 권한 요청
                ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult( //위치권한 요청
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d("problem","위치권한을 요청합니다..")
        when(requestCode){
            REQUEST_LOCATION_PERMISSION ->{
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   // showCurrentLocationOnMap()
                }
                else{
                    Log.d("problem","위치권한이 없어요!")
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        Log.d("problem","현재 위치를 파악하고있습니다")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    val currentLatLng = LatLng(it.latitude, it.longitude)
                    Log.d("problem","위도:  ${currentLatLng.latitude}, 경도 : ${currentLatLng.longitude}")
                    googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                    googleMap?.addMarker(MarkerOptions().position(currentLatLng).title("현재 위치"))
                }
            }
            .addOnFailureListener{
                e-> Toast.makeText(context,"위치정보를 못가져왓습니다",Toast.LENGTH_SHORT).show()
            }
    }

    /*
    @SuppressLint("MissingPermission")
    private fun showCurrentLocationOnMap() {
        googleMap?.isMyLocationEnabled = true
        getCurrentLocation { location ->
            location?.let {
                val currentLatLng = LatLng(it.latitude, it.longitude)
                Log.d("problem","위도 : ${currentLatLng.latitude}, 경도 : ${currentLatLng.longitude}")
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                googleMap?.addMarker(MarkerOptions().position(currentLatLng).title("현재 위치"))
            }
        }
    }
     */
    @SuppressLint("MissingPermission")
    private fun showCustomLocationOnMap() {
        //val latitude = 35.888085 // 지정할 위도 값
        //val longitude = 128.611408 // 지정할 경도 값
        val latitude: Double? = 35.888085 // 지정할 위도 값
        val longitude: Double? = 128.611408 // 지정할 경도 값


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