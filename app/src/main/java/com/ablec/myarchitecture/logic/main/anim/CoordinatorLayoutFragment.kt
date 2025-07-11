package com.ablec.myarchitecture.logic.main.anim

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ablec.lib.base.BaseFragment
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.FragmentMotionLayoutCoordinatorBinding
import com.ablec.myarchitecture.logic.other.MyListFragment
import com.blankj.utilcode.util.LogUtils
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2022/9/17 16:39
 */
class CoordinatorLayoutFragment : BaseFragment(R.layout.fragment_motion_layout_coordinator) {

    private val mBinding: FragmentMotionLayoutCoordinatorBinding by viewBinding()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewPager.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return 4
            }

            override fun createFragment(position: Int): Fragment {
                return MyListFragment()
            }
        }
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "视频"
                1 -> tab.text = "推荐"
                2 -> tab.text = "小sssssss说"
                3 -> tab.text = "游戏"
            }
        }.attach()

        mBinding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                LogUtils.d(verticalOffset) // 0 ~ -400
        })
    }



}