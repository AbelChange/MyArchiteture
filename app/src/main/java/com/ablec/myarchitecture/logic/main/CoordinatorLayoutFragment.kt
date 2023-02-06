package com.ablec.myarchitecture.logic.main

import android.os.Bundle
import android.view.View
import com.ablec.myarchitecture.R
import com.ablec.lib.base.BaseFragment
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.databinding.CoordinatorLayoutFragmentBinding

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2022/9/17 16:39
 */
class CoordinatorLayoutFragment : BaseFragment(R.layout.coordinator_layout_fragment) {

    private val mBinding: CoordinatorLayoutFragmentBinding by viewBinding()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}