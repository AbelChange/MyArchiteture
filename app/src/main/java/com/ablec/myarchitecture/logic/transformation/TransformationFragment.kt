package com.ablec.myarchitecture.logic.transformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ablec.myarchitecture.databinding.TransformationFragmentBinding

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/2/2 15:54
 */
class TransformationFragment : Fragment() {

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
            println("distinct" + it.toString())
        })
        viewModel.mapAgeOnly().observe(viewLifecycleOwner) {
            println(it)
        }
    }

}