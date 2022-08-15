package com.ablec.lib.util

import android.view.View

/**
 * @Description:
 * @Author: haoshuaihui
 * @CreateDate: 2019/8/13 20:17
 */
object ClickUtil {
    /**
     * 最近一次点击的时间
     */
    private var mLastClickTime: Long = 0

    /**
     * 最近一次点击的控件ID
     */
    private var mLastClickViewId = 0

    /**
     * 是否是快速点击
     *
     * @param v              点击的控件
     * @return true:是，false:不是
     */
    fun isFastClick(v: View): Boolean {
        val viewId = v.id
        val time = System.currentTimeMillis()
        val timeInterval = Math.abs(time - mLastClickTime)
        return if (timeInterval < 1000 && viewId == mLastClickViewId) {
            true
        } else {
            mLastClickTime = time
            mLastClickViewId = viewId
            false
        }
    }

    fun isFastClick(v: View, millionSeconds: Long): Boolean {
        val viewId = v.id
        val time = System.currentTimeMillis()
        val timeInterval = Math.abs(time - mLastClickTime)
        return if (timeInterval < millionSeconds && viewId == mLastClickViewId) {
            true
        } else {
            mLastClickTime = time
            mLastClickViewId = viewId
            false
        }
    }

    /**
     * 是否是快速点击
     *
     * @return true:是，false:不是
     */
    val isFastClick: Boolean
        get() {
            val time = System.currentTimeMillis()
            val timeInterval = Math.abs(time - mLastClickTime)
            return if (timeInterval < 1000) {
                true
            } else {
                mLastClickTime = time
                false
            }
        }

    /**
     * 是否是快速点击
     *
     * @return true:是，false:不是
     */
    fun isFastClick(clickID: Int): Boolean {
        val time = System.currentTimeMillis()
        if (mLastClickViewId != clickID) {
            mLastClickViewId = clickID
            mLastClickTime = time
            return false
        }
        val timeInterval = Math.abs(time - mLastClickTime)
        return if (timeInterval < 1000) {
            true
        } else {
            mLastClickTime = time
            mLastClickViewId = clickID
            false
        }
    }
}