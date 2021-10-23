package com.yunfeng.lib.dialog.dialogmanager

import android.app.Activity
import androidx.fragment.app.FragmentManager

/**
 * @Description:    优先级dialog
 * @Author:         haoshuaihui
 * @CreateDate:     2021/7/13 10:15
 */
interface IPriorityDialog {

    fun show(activity: Activity, manager: FragmentManager, tag: String?)

    fun setOnDismissListener(listener: OnDismissListener)

    fun getPriority(): Int

    companion object Priority {
        //升级弹窗 已用
        const val IMMEDIATE: Int = 100
        const val HIGH: Int = 99
        const val NORMAL: Int = 98
        const val LOW: Int = 97
    }

    interface OnDismissListener {
        fun onDismiss()
    }
}

