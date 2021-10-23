package com.temption.myarchitecture.logic.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.temption.myarchitecture.databinding.MainFragmentBinding
import com.yunfeng.lib.ext.findNavController
import com.yunfeng.module_base.service.RouterServiceManager

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnConstraintLayout.setOnClickListener {
            val action =
                MainFragmentDirections.actionMainFragmentToConstraintLayoutFragment()
            findNavController().navigate(action)
        }

        binding.btnTransformation.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToTransformationFragment()
            findNavController().navigate(action)
        }

        binding.btnSocket.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToWebSocketFragment())
        }

        binding.btnRxOperator.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToRxFragment())
        }

        binding.btnGoLogin.setOnClickListener {
            RouterServiceManager.getAccountService()?.startLogin(requireContext())
        }

        binding.btnGoList.setOnClickListener{
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDataListFragment())
        }

    }





}