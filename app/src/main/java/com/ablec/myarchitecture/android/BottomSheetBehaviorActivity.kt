package com.ablec.myarchitecture.android

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.View
import android.view.Window
import com.ablec.lib.base.BaseActivity
import com.ablec.lib.ext.immerse
import com.ablec.lib.ext.paddingNavigationBar
import com.ablec.lib.ext.replaceStatusBar
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN

/**
 * @author shuaihui_hao
 * @date 2024/7/4
 * @description
 */
class BottomSheetBehaviorActivity : BaseActivity() {

    private val binding: FragmentBottomSheetBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        setEnterTransition(window)
        super.onCreate(savedInstanceState)
        immerse()
        setContentView(binding.root)
        binding.root.paddingNavigationBar()
        binding.statusBar.replaceStatusBar()
        initView()
        initWeb()
    }

    private fun setEnterTransition(window: Window) {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        val slide = Slide().apply {
            duration = 200L
        }
        window.enterTransition = slide
    }

    private fun initWeb() {
        binding.webview.apply {
            settings.javaScriptEnabled = true;
            webViewClient = android.webkit.WebViewClient()
            loadUrl("https://www.baidu.com/")
        }
    }

    private fun initView() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val behavior = BottomSheetBehavior.from<View>(binding.bottomSheet)
        behavior.state = STATE_COLLAPSED
        // 设置底部工作表的状态监听器和行为
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                Log.i("BottomSheetBehavior", "newState: $newState")
                if (newState == STATE_EXPANDED) {
                    behavior.isDraggable = false
                } else if (newState == STATE_HIDDEN) {
                    finish()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.i("BottomSheetBehavior", "slideOffset: $slideOffset")
                if (slideOffset > 0f) {
                    binding.toolBar.alpha = slideOffset
                    binding.statusBar.alpha = slideOffset
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun start(context: Activity) {
            val starter = Intent(context, BottomSheetBehaviorActivity::class.java)
            context.startActivity(
                starter, ActivityOptions.makeSceneTransitionAnimation(context)
                    .toBundle()
            )
        }
    }

}