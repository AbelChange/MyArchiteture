package com.ablec.myarchitecture.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ablec.myarchitecture.databinding.FragmentTestBinding

import com.ablec.myarchitecture.logic.transformation.TransformationFragment
import com.blankj.utilcode.util.ToastUtils

class TestFragment : Fragment() {

    private lateinit var binding: FragmentTestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ll.isInterceptClick = true

        binding.btn.setOnClickListener {
            ToastUtils.showShort("点击")
        }
        binding.vp.adapter = object :FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return 5
            }

            override fun createFragment(position: Int): Fragment {
                return TransformationFragment()
            }
        }

    }


}