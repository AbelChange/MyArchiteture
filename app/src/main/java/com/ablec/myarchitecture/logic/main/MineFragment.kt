package com.ablec.myarchitecture.logic.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ablec.lib.ext.viewBinding
import com.ablec.module_base.service.RouterServiceManager
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.FragmentMineBinding

class MineFragment : Fragment(R.layout.fragment_mine) {

    private val binding: FragmentMineBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGoLogin.setOnClickListener {
            RouterServiceManager.getAccountService()?.startLogin(requireContext())
        }
    }


}