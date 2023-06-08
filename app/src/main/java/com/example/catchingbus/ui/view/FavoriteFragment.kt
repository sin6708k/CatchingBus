/*
package com.example.catchingbus.ui.view

import Dummy
import MenuFragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import com.example.catchingbus.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 이 프래그먼트에 해당하는 레이아웃을 반환합니다.
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 프레그먼트 생성시 전달받은 매개변수에서 Dummy 객체를 추출합니다.
        // 만약 예를 들어 Dummy를 Favoritelist로 변경한다면 as? Dummy를 Favoritelist로 변경하면된다.
        val dummy = arguments?.getSerializable("dummy") as? Dummy

        // UI 요소들을 초기화합니다.
        val stationTextView = view.findViewById<TextView>(R.id.stationText)
        val busTextView = view.findViewById<TextView>(R.id.busText)
        var timeStart = view.findViewById<TextView>(R.id.StartText)
        var timeEnd = view.findViewById<TextView>(R.id.TextView001)

        // Dummy 객체의 데이터를 TextViews에 설정합니다.
        dummy?.let {
            stationTextView.text = it.station
            busTextView.text = it.bus
            timeStart.text=it.StartTime
            timeEnd.text=it.EndTime
        }

        // 뒤로 가기 버튼 클릭 시, 이 프래그먼트를 제거합니다.
        val imageViewBack = view.findViewById<ImageView>(R.id.imageView_back)
        imageViewBack.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        // 삭제 버튼 설정, 이 버튼은 timeStart와 timeEnd TextViews를 클리어합니다.
        val xImageView=view.findViewById<ImageView>(R.id.imageView2)
        xImageView.setOnClickListener{
            timeStart.text = ""
            timeEnd.text = ""
            dummy?.let {
                it.StartTime = ""
                it.EndTime = ""
            }
        }

        // 팝업창 관련 설정
        val imageView = view.findViewById<ImageView>(R.id.imageView11)
        imageView.setOnClickListener {
            val layoutInflater = LayoutInflater.from(context)
            val popupView = layoutInflater.inflate(R.layout.popup_window, null)

            // 팝업창 내의 UI 요소들을 초기화합니다.
            val editText = popupView.findViewById<EditText>(R.id.enterStart)
            val editText1 = popupView.findViewById<EditText>(R.id.enterEnd)
            val submitButton = popupView.findViewById<ImageView>(R.id.submit_button)

            val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            popupWindow.isFocusable = true

            val closeButton = popupView.findViewById<ImageView>(R.id.close_popup)
            closeButton.setOnClickListener {
                // 팝업창 닫기 버튼 클릭 시, 팝업창을 닫습니다.
                popupWindow.dismiss()
            }

            submitButton.setOnClickListener {
                // 입력된 시간을 추출하고 그 값을 TextViews와 Dummy 객체에 설정합니다.
                val time_s = editText.text.toString()
                val time_e = editText1.text.toString()
                timeStart?.text = time_s
                timeEnd?.text = time_e

                //인자로 받은 Dummy의 StartTime,EndTime에 입력된 값을 삽입한다.
                dummy?.let {
                    it.StartTime = time_s
                    it.EndTime = time_e
                }

                // 팝업창을 닫습니다.
                popupWindow.dismiss()
            }
            // 특정 위치에 팝업창을 보여줍니다.
            popupWindow.showAtLocation(popupView, Gravity.TOP, 0, 330)
        }
    }






    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

 */