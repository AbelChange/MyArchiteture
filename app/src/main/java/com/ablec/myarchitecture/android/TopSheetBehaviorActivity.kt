package com.ablec.myarchitecture.android

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import com.ablec.lib.base.BaseActivity
import com.ablec.lib.ext.immerse
import com.ablec.lib.ext.paddingNavigationBar
import com.ablec.lib.ext.replaceStatusBar
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.databinding.FragmentBottomSheetBinding
import com.ablec.myarchitecture.databinding.FragmentTopSheetBinding
import com.ablec.myarchitecture.view.TopSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN

/**
 * @author shuaihui_hao
 * @date 2024/7/4
 * @description
 */
class TopSheetBehaviorActivity : BaseActivity() {

    private val binding: FragmentTopSheetBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        setEnterTransition(window)
        super.onCreate(savedInstanceState)
        immerse()
        setContentView(binding.root)
        binding.root.paddingNavigationBar()
        binding.statusBar.replaceStatusBar()
        initView()
    }

    private fun setEnterTransition(window: Window) {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        val slide = Slide().apply {
            slideEdge = Gravity.TOP
            duration = 200L
        }
        window.enterTransition = slide
    }


    private fun initView() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val behavior = TopSheetBehavior.from<View>(binding.bottomSheet)
        behavior.state = TopSheetBehavior.STATE_COLLAPSED
        // 设置底部工作表的状态监听器和行为
        behavior.setTopSheetCallback(object : TopSheetBehavior.TopSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float, isOpening: Boolean?) {
                Log.i("BottomSheetBehavior", "slideOffset: $slideOffset")
                if (slideOffset > 0f) {
                    binding.toolBar.alpha = slideOffset
                    binding.statusBar.alpha = slideOffset
                }
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                Log.i("BottomSheetBehavior", "newState: $newState")
                if (newState == STATE_EXPANDED) {
//                    behavior.isDraggable = false
                } else if (newState == STATE_HIDDEN) {
                    finish()
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun start(context: Activity) {
            val starter = Intent(context, TopSheetBehaviorActivity::class.java)
            context.startActivity(
                starter, ActivityOptions.makeSceneTransitionAnimation(context)
                    .toBundle()
            )
        }
    }

}