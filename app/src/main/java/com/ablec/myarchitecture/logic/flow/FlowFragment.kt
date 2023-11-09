package com.ablec.myarchitecture.logic.flow

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ablec.lib.ext.debounceClick
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.FragmentFlowBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FlowFragment : Fragment(R.layout.fragment_flow) {

    private val viewModel by viewModels<FlowViewModel>()

    private val binding: FragmentFlowBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //点击防抖
        binding.btnStart.debounceClick(5000) {
            viewModel.test()
        }

        lifecycleScope.launch {
            viewModel
                .getSpeed()
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    binding.textViewSpeed.text = "当前速度$it"
                    binding.textViewSpeedX.text = "超速了$it"
                }
        }

        lifecycleScope.launch {
            viewModel
                .getIfSpeedX()
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    binding.textViewSpeedX.isVisible = it
                }
        }


    }


}