package com.ablec.myarchitecture.logic.main

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()
    private lateinit var navController: NavController

    private val backPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            navController.navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            backPressedCallback.isEnabled = navController.currentDestination?.id != R.id.mainFragment
        }

        val graph = navController.graph.apply {
            //可动态设置start
            // graph.startDestination = R.id.xxx
        }
        //初始参数
        navController.setGraph(graph, Bundle().apply { })
        //联动toolbar rootFragment不显示返回按钮
        val appBarConfiguration =
            AppBarConfiguration.Builder(navController.graph)
                .build()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,backPressedCallback)
    }

    override fun onResume() {
        super.onResume()
        backPressedCallback.isEnabled = navController.currentDestination?.id != R.id.mainFragment
    }

    override fun onPause() {
        super.onPause()
        backPressedCallback.isEnabled = false
    }


}