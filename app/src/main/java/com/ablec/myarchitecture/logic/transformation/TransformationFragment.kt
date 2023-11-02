package com.ablec.myarchitecture.logic.transformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.ablec.myarchitecture.databinding.TransformationFragmentBinding
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/2/2 15:54
 */
class TransformationFragment : Fragment() {
    private val TAG = "TransformationFragment"

    private lateinit var binding: TransformationFragmentBinding

    private val viewModel: TransformationViewModel by viewModels {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TransformationFragmentBinding.inflate(inflater);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonStart.setOnClickListener {
            viewModel.start()
        }
        viewModel.distinctPerson().observe(viewLifecycleOwner, Observer {
            LogUtils.d(TAG, it)
        })
        viewModel.mapAgeOnly().observe(viewLifecycleOwner) {
            println(it)
            LogUtils.d(TAG, it)
        }



        lifecycleScope.launch {
            viewModel.downStream
                //绑定生命周期
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {

                }
        }

    }

}