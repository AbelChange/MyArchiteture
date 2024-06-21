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

/**
 * 使用navigation跳转
 */
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()
    private lateinit var navController: NavController

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            //判断谁来处理回退事件
            if (navController.currentDestination?.id != R.id.mainFragment){
                navController.navigateUp()
            }else{
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

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


}