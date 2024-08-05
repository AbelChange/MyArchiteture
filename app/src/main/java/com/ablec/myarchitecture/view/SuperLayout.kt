package com.ablec.myarchitecture.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2023/2/8 15:48
 */
class SuperLayout : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.d("事件", "Super-dispatchTouchEvent开始,action：${event?.action}")
        val dispatchTouchEvent = super.dispatchTouchEvent(event)
        Log.d("事件", "Super-dispatchTouchEvent结果:${dispatchTouchEvent},action：${event?.action}")
        return dispatchTouchEvent
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        Log.d(
            "事件",
            "Super-onInterceptTouchEvent开始,action：${event?.action}"
        )
        val onInterceptTouchEvent = super.onInterceptTouchEvent(event)
        Log.d(
            "事件",
            "Super-onInterceptTouchEvent结果:${onInterceptTouchEvent},action：${event?.action}"
        )
        return onInterceptTouchEvent
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("事件", "Super-onTouchEvent:开始,action：${event?.action}")
        val onTouchEvent = super.onTouchEvent(event)
        Log.d("事件", "Super-onTouchEvent结果:${onTouchEvent},action：${event?.action}")
        return onTouchEvent
    }

}