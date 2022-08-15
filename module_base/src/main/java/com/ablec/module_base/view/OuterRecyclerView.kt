package com.ablec.module_base.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView

/**
 * @Description: 外部拦截法，解决滑动冲突
 * @Author: haoshuaihui
 * @CreateDate: 2020/11/22 14:59
 */
class OuterRecyclerView : RecyclerView {
    private var mScrollPointerId = 0
    private var mInitialTouchX = 0
    private var mInitialTouchY = 0
    private var mTouchSlop = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        val vc = ViewConfiguration.get(context)
        this.mTouchSlop = vc.scaledTouchSlop
    }

    override fun setScrollingTouchSlop(slopConstant: Int) {
        val vc = ViewConfiguration.get(this.context)
        when (slopConstant) {
            0 -> {
                this.mTouchSlop = vc.scaledTouchSlop
                this.mTouchSlop = vc.scaledPagingTouchSlop
            }
            1 -> this.mTouchSlop = vc.scaledPagingTouchSlop
            else -> Log.w(
                "RecyclerView",
                "setScrollingTouchSlop(): bad argument constant $slopConstant; using default value"
            )
        }
        super.setScrollingTouchSlop(slopConstant)
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        val canScrollHorizontally = layoutManager!!.canScrollHorizontally()
        val canScrollVertically = layoutManager!!.canScrollVertically()
        val action = e.actionMasked
        val actionIndex = e.actionIndex
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mScrollPointerId = e.getPointerId(0)
                this.mInitialTouchX = (e.x + 0.5f).toInt()
                this.mInitialTouchY = (e.y + 0.5f).toInt()
                return super.onInterceptTouchEvent(e)
            }
            MotionEvent.ACTION_MOVE -> {
                val index = e.findPointerIndex(this.mScrollPointerId)
                if (index < 0) {
                    return false
                }
                val x = (e.getX(index) + 0.5f).toInt()
                val y = (e.getY(index) + 0.5f).toInt()
                if (scrollState != 1) {
                    val dx = x - this.mInitialTouchX
                    val dy = y - this.mInitialTouchY
                    var startScroll = false
                    if (canScrollHorizontally && Math.abs(dx) > this.mTouchSlop && Math.abs(dx) > Math.abs(
                            dy
                        )
                    ) {
                        startScroll = true
                    }
                    if (canScrollVertically && Math.abs(dy) > this.mTouchSlop && Math.abs(dy) > Math.abs(
                            dx
                        )
                    ) {
                        startScroll = true
                    }
                    return startScroll && super.onInterceptTouchEvent(e)
                }
                return super.onInterceptTouchEvent(e)
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                this.mScrollPointerId = e.getPointerId(actionIndex)
                this.mInitialTouchX = (e.getX(actionIndex) + 0.5f).toInt()
                this.mInitialTouchY = (e.getY(actionIndex) + 0.5f).toInt()
                return super.onInterceptTouchEvent(e)
            }
        }
        return super.onInterceptTouchEvent(e)
    }
}