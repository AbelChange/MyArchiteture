package com.ablec.myarchitecture.logic.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ablec.lib.ext.navigate
import com.ablec.module_base.service.RouterServiceManager
import com.ablec.myarchitecture.databinding.RouterFragmentBinding

class RouterFragment : Fragment() {

    private lateinit var binding: RouterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RouterFragmentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnConstraintLayout.setOnClickListener {
            val action =
                RouterFragmentDirections.actionMainFragmentToConstraintLayoutFragment()
            navigate(action)
        }

        binding.btnCoordinator.setOnClickListener {
            val action = RouterFragmentDirections.actionMainFragmentToCoordinatorLayoutFragment()
            navigate(action)
        }

        binding.btnTransformation.setOnClickListener {
            val action = RouterFragmentDirections.actionMainFragmentToTransformationFragment()
            navigate(action)
        }

        binding.btnSocket.setOnClickListener {
            navigate(RouterFragmentDirections.actionMainFragmentToWebSocketFragment())
        }

        binding.btnRxOperator.setOnClickListener {
            navigate(RouterFragmentDirections.actionMainFragmentToRxFragment())
        }

        binding.btnGoLogin.setOnClickListener {
            RouterServiceManager.getAccountService()?.startLogin(requireContext())
        }


        binding.btnAnim.setOnClickListener {
            navigate(RouterFragmentDirections.actionMainFragmentToAnimFragment())
        }

        binding.btnGoList.setOnClickListener {
            navigate(RouterFragmentDirections.actionMainFragmentToMyListFragment())
        }

        binding.btnPicker.setOnClickListener {
            navigate(RouterFragmentDirections.actionMainFragmentToPickerFragment())
        }
        binding.btnBinder.setOnClickListener {
            navigate(RouterFragmentDirections.actionMainFragmentToBinderFragment())
        }

    }


}