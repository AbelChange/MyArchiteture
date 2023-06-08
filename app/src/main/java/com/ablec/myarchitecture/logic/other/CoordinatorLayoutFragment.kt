package com.ablec.myarchitecture.logic.other

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ablec.lib.base.BaseFragment
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.CoordinatorLayoutFragmentBinding
import com.blankj.utilcode.util.LogUtils
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2022/9/17 16:39
 */
class CoordinatorLayoutFragment : BaseFragment(R.layout.coordinator_layout_fragment) {

    private val mBinding: CoordinatorLayoutFragmentBinding by viewBinding()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewPager.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return 5
            }

            override fun createFragment(position: Int): Fragment {
                return MyListFragment()
            }
        }
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "视频"
                1 -> tab.text = "推荐"
                3 -> tab.text = "小说"
                4 -> tab.text = "游戏"
            }
        }.attach()

        mBinding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                LogUtils.d(verticalOffset) // 0 ~ -400
        })
    }



}