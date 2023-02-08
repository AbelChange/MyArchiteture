package com.ablec.myarchitecture.logic.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ablec.lib.ext.navigate
import com.ablec.module_base.service.RouterServiceManager
import com.ablec.myarchitecture.databinding.MainFragmentBinding

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
            navigate(action)
        }

        binding.btnCoordinator.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToCoordinatorLayoutFragment()
            navigate(action)
        }

        binding.btnTransformation.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToTransformationFragment()
            navigate(action)
        }

        binding.btnSocket.setOnClickListener {
            navigate(MainFragmentDirections.actionMainFragmentToWebSocketFragment())
        }

        binding.btnRxOperator.setOnClickListener {
            navigate(MainFragmentDirections.actionMainFragmentToRxFragment())
        }

        binding.btnGoLogin.setOnClickListener {
            RouterServiceManager.getAccountService()?.startLogin(requireContext())
        }

        binding.btnTest.setOnClickListener {
            navigate(MainFragmentDirections.actionMainFragmentToTestFragment())
//            RouterServiceManager.getAccountService()?.startLogin(requireContext())
        }


    }


}