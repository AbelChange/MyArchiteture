package com.ablec.myarchitecture.logic.main.recent

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ablec.lib.ext.debounceClick
import com.ablec.lib.ext.viewBinding
import com.ablec.module_base.service.RouterServiceManager
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.FragmentRecentBinding

class RecentFragment : Fragment(R.layout.fragment_recent){
    private val binding: FragmentRecentBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGoLogin.debounceClick{
            RouterServiceManager.getAccountService()?.startLogin(requireContext())
        }
    }

}


