package com.ablec.myarchitecture.logic.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ablec.myarchitecture.databinding.ConstraintFragmentBinding

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/1/8 16:59
 */
class ConstraintLayoutFragment : Fragment() {

    private lateinit var binding: ConstraintFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ConstraintFragmentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}