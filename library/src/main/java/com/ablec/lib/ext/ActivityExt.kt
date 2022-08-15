package com.ablec.lib.ext

import android.app.Activity
import android.view.View
import androidx.core.view.marginTop
import com.ablec.lib.ext.dp
import com.blankj.utilcode.util.BarUtils

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/29 13:28
 */


//沉浸 + 透明状态栏
fun Activity.setUpBars(lightMode: Boolean) {
    BarUtils.transparentStatusBar(window)
    BarUtils.setStatusBarLightMode(window, lightMode)
    BarUtils.setNavBarLightMode(window, lightMode)
}

fun Activity.hideSystemBar() {
    BarUtils.setNavBarVisibility(window, false)
    BarUtils.setStatusBarVisibility(window, false)
}

val statusBarHeight = BarUtils.getStatusBarHeight()

fun View.paddingStatusBar() {
    setPadding(paddingLeft, statusBarHeight + 12.dp, paddingRight, paddingBottom)
}





