package com.yunfeng.lib.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.yunfeng.common.R

open class LoadingDialog @JvmOverloads constructor(
    context: Context,
    private var windowIsTranslucent: Boolean = false
) : Dialog(context), LifecycleObserver {
    private lateinit var mTitleTextView: TextView

    override fun onStart() {
        super.onStart()
        val window: Window = window ?: return
        if (windowIsTranslucent) {
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.windowManager.currentWindowMetrics
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            window.setDimAmount(0f)
        } else {
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.loading_dialog)
        mTitleTextView = findViewById(R.id.tv_content)!!
        if (windowIsTranslucent) {
            mTitleTextView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun destroy() {
        dismiss()
    }
}