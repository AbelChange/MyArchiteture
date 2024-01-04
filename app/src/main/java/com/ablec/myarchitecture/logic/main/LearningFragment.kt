package com.ablec.myarchitecture.logic.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.ablec.lib.ext.viewBinding
import com.ablec.module_base.service.RouterServiceManager
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.FragmentLearningBinding

class LearningFragment:Fragment(R.layout.fragment_learning) {

    private val binding:FragmentLearningBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTest.setOnClickListener{
            object : Thread() {
                override fun run() {
                    super.run()
                    var i = 1
                    while (true) {
                        i++
                        val result = ByteArray(5 * 1024 * 1024)
                        Log.d("内存申请", i.toString() + "")
                    }
                }
            }.start()
        }

        binding.btnNative.setOnClickListener{
            RouterServiceManager.getNativeService()?.startNativeUi(requireContext())
        }

        binding.btnCompose.setOnClickListener{
            RouterServiceManager.getComposeService()?.start(requireContext())
        }
    }


}