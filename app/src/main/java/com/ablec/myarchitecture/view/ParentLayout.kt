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
class ParentLayout : FrameLayout {

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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d("事件", "Parent-dispatchTouchEvent,action：${ev?.action}")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        Log.d("事件", "Parent-onInterceptTouchEvent,action：${ev?.action}")
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("事件", "Parent-onTouchEvent,action：${event?.action}")
        return false
    }
}