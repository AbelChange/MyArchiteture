package com.ablec.myarchitecture.base

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.FragmentHomeBinding

/**
 * 使用navigation跳转
 */
abstract class NavigationFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()
    private lateinit var navController: NavController

    private val backPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            navController.navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 将 Toolbar 设置为支持 ActionBar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        if (savedInstanceState == null){
            navController = navHostFragment.navController
            navController.setGraph(getGraphResId())
            val appBarConfiguration =
                AppBarConfiguration.Builder(navController.graph)
                    .build()
            NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration)
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,backPressedCallback)
    }

    abstract fun getGraphResId() :Int

}