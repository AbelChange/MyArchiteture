package com.ablec.myarchitecture.logic.main

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import com.ablec.lib.ext.viewBinding
import com.ablec.module_base.service.RouterServiceManager
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.FragmentMineBinding

class MineFragment : Fragment(R.layout.fragment_mine) {

    private val binding: FragmentMineBinding by viewBinding()

    //只消费点击事件的识别器
    private val gestureDetector: GestureDetector by lazy {
        GestureDetector(requireContext(), object : SimpleOnGestureListener() {
//            override fun onDoubleTap(e: MotionEvent): Boolean {
//                Log.e("MineFragment", "双击")
//                return true
//            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                Log.e("MineFragment", "单击")
                return true
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.btnGoLogin.setOnTouchListener { v, event ->
//            return@setOnTouchListener gestureDetector.onTouchEvent(event)
//        }
        binding.btnGoLogin.setOnClickListener {
            RouterServiceManager.getAccountService()?.startLogin(requireContext())
        }
    }

}