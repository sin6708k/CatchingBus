package com.example.catchingbus.ui.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catchingbus.R
import com.example.catchingbus.data.Schedule
import com.example.catchingbus.data.Station
import com.example.catchingbus.databinding.ActivityMainBinding
import com.example.catchingbus.databinding.FragmentScheduleBinding
import com.example.catchingbus.databinding.FragmentSearchBinding
import com.example.catchingbus.databinding.PopupWindowBinding
import com.example.catchingbus.ui.adapter.ScheduleAdapter
import com.example.catchingbus.ui.adapter.StationSearchAdapter
import com.example.catchingbus.viewmodel.FavoriteViewModel
import com.example.catchingbus.viewmodel.SearchViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleFragment : Fragment(),ScheduleAdapter.OnScheduleRemoveClickListener {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentScheduleBinding? = null
    private val binding: FragmentScheduleBinding get() = _binding!!
    private lateinit var mainActivity: MainActivity
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var sharedViewModel : SearchViewModel
    private lateinit var favoriteViewModel : FavoriteViewModel
    private lateinit var scheduleAdapter: ScheduleAdapter
    val Mainbinding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Mainbinding.textLayout.visibility=View.VISIBLE
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        favoriteViewModel = ViewModelProvider(requireActivity()).get(FavoriteViewModel::class.java)
        mainBinding = mainActivity.binding

        setupRecyclerView()
        scheduleAdapter.setRemoveScheduleClickListener(this)
        binding.addAlarm.setOnClickListener {
            showDialog()
        }
        binding.StationTextView.text=favoriteViewModel.selectedFavorite.value?.station?.name.toString()
        binding.BusTextView.text=favoriteViewModel.selectedFavorite.value?.bus?.name.toString()
        favoriteViewModel.schedules.observe(viewLifecycleOwner) { newSchedule ->
            Log.d("problem", "값 변화: $newSchedule")
            scheduleAdapter.submitList(newSchedule)
            scheduleAdapter.notifyDataSetChanged() // 변경된 부분
        }
        return binding.root
    }
    fun showDialog(){
        var dialogBinding = PopupWindowBinding.inflate(layoutInflater)
        var dialog = this.context?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(dialogBinding.root)
        dialog?.setCancelable(false)
        dialog?.show()
        dialogBinding.closePopup.setOnClickListener {  //닫기버튼
            dialog?.dismiss()
        }
        dialogBinding.submitButton.setOnClickListener {
            val first = dialogBinding.StartTimeText.text.toString().trim()
            val second = dialogBinding.endTimeText.text.toString().trim()
            //val formatter = DateTimeFormatter.ofPattern("HH:mm")
            if(first.length!=5 || second.length!=5){ //정확한 형식이 아니라면
                Toast.makeText(requireContext(),"시간 형식을 지켜주세요\n(HH:MM)입니다",Toast.LENGTH_SHORT).show()
            }
            else {
                val startTime = LocalTime.parse(first)
                val endTime = LocalTime.parse(second)
                Log.d("problem", "ADD버튼")
                Log.d("problem", "시간 : ${startTime}, ${endTime}")
                favoriteViewModel.addSchedule(startTime, endTime)
                //val newScheduleList = favoriteViewModel.schedules.value.orEmpty()
                //scheduleAdapter.submitScheduleList(newScheduleList)
                dialog?.dismiss()
            }
        }
        dialog?.window?.setLayout(1000, 1000)
    }
    private fun setupRecyclerView() { //리사이클러뷰 규성.
        Log.d("problem", "스케쥴 리사이클러뷰구성합니다.")
        scheduleAdapter = ScheduleAdapter()
        binding.scheduleRecycler.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = scheduleAdapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onScheduleRemoveClick(schedule: Schedule) {
        Log.d("problem","스케줄프래그먼트, 삭제스케줄")
        favoriteViewModel.removeSchedule(schedule)
    }

    override fun onResume() {
        super.onResume()
    }
}