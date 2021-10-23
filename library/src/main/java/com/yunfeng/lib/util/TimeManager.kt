package com.yunfeng.lib.util

import android.os.SystemClock

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/6/10 15:33
 */
object TimeManager {

    private var isServerTime = false
    private var differenceTime: Long = 0

    /**
     * 获取当前时间
     *
     * @return the time
     */
    fun getServiceTime(): Long {
        if (!isServerTime) {
            return System.currentTimeMillis()
        }
        //时间差加上当前手机启动时间就是准确的服务器时间了
        return differenceTime + SystemClock.elapsedRealtime();
    }

    /**
     * 时间校准
     *
     * @param lastServiceTime 当前服务器时间
     * @return the long
     */
    fun setServiceTime(lastServiceTime: Long) {
        //记录时间差
        differenceTime = lastServiceTime - SystemClock.elapsedRealtime()
        isServerTime = true
    }
}