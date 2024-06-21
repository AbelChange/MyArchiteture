package com.ablec.myarchitecture.logic.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ablec.lib.base.BaseActivity
import com.ablec.lib.ext.immerse
import com.ablec.lib.ext.paddingStatusBar
import com.ablec.module_base.config.Main.BASE
import com.ablec.myarchitecture.databinding.MainActivityBinding
import com.ablec.myarchitecture.logic.pageslist.DataListModel
import com.sankuai.waimai.router.annotation.RouterUri
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@RouterUri(path = [BASE])
class MainActivity : BaseActivity() {

    private lateinit var binding: MainActivityBinding

    private val vm by viewModels<DataListModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        immerse(true)
        binding.root.paddingStatusBar()
        initView()
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
                0 -> HomeFragment()
                1 -> MotionLayoutFragment()
                2->LearningFragment()
                else -> MineFragment()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}