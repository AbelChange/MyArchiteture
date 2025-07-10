package com.ablec.myarchitecture.logic.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ablec.lib.base.BaseActivity
import com.ablec.lib.ext.paddingStatusBar
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.base.NavigationFragment
import com.ablec.myarchitecture.databinding.MainActivityBinding
import com.ablec.myarchitecture.logic.main.recent.RecentFragment
import com.ablec.myarchitecture.logic.pageslist.DataListModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: MainActivityBinding

    private val vm by viewModels<DataListModel>()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        binding.root.paddingStatusBar()
        initView()
        getMeasuredWidthHeight()
    }

    private fun getMeasuredWidthHeight() {

        //拿不到
        Handler(Looper.getMainLooper()).post {
            Log.d(
                "MainActivity-handler.post",
                "width:${binding.root.width},height:${binding.root.height} "
            )
        }
        //可以
        binding.root.doOnLayout {
            Log.d(
                "MainActivity-doOnLayout",
                "width:${binding.root.width},height:${binding.root.height} "
            )
        }
        //可以
        binding.root.doOnPreDraw {
            Log.d(
                "MainActivity-doOnPreDraw",
                "width:${binding.root.width},height:${binding.root.height} "
            )
        }
        //可以
        binding.root.post {
            Log.d(
                "MainActivity-view.post",
                "width:${binding.root.width},height:${binding.root.height} "
            )
        }
    }


    private fun initView() {
        binding.viewPager.apply {
            isUserInputEnabled = false
            adapter = NavigationPagerAdapter(this@MainActivity)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.navigation.menu.getItem(position).isChecked = true
                }
            })
        }
        binding.navigation.setOnItemSelectedListener {
            binding.viewPager.setCurrentItem(it.order, false)
            return@setOnItemSelectedListener true
        }

    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }

    class NavigationPagerAdapter(ac: FragmentActivity) : FragmentStateAdapter(ac) {
        override fun getItemCount(): Int {
            return 4
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> WidgetFragment()
                1 -> AnimFragment()
                2 -> ModuleFragment()
                else -> RecentFragment()
            }
        }
    }

    class WidgetFragment : NavigationFragment() {
        override fun getGraphResId(): Int {
            return R.navigation.nav_graph_widget
        }
    }

    class AnimFragment : NavigationFragment() {
        override fun getGraphResId(): Int {
            return R.navigation.nav_graph_anim
        }
    }

    class ModuleFragment : NavigationFragment() {
        override fun getGraphResId(): Int {
            return R.navigation.nav_graph_module
        }
    }




}