package com.ablec.myarchitecture.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatButton

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2023/2/8 15:48
 */
class ChildView : AppCompatButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.d("事件", "Child-dispatchTouchEvent:开始,action：${event?.action}")
        val dispatchTouchEvent = super.dispatchTouchEvent(event)
        Log.d("事件", "Child-dispatchTouchEvent:结果${dispatchTouchEvent},action：${event?.action}")
        return dispatchTouchEvent
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("事件", "Child-onTouchEvent:开始,action：${event?.action}")
        val onTouchEvent = super.onTouchEvent(event)
//        val onTouchEvent = false
        Log.d("事件", "Child-onTouchEvent:结果${onTouchEvent},action：${event?.action}")
        return onTouchEvent
    }

}