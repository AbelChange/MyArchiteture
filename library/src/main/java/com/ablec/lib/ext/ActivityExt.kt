package com.ablec.lib.ext

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.ablec.lib.ext.dp
import com.blankj.utilcode.util.BarUtils

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/29 13:28
 */
val Activity.context:Context
    get() {
        return this
    }

//沉浸 + 透明状态栏
fun Activity.setUpBars(lightMode: Boolean) {
    BarUtils.transparentStatusBar(window)
    WindowInsetsControllerCompat(this.window, this.window.decorView).apply {
        isAppearanceLightStatusBars = lightMode
    }
}

fun Activity.hideSystemBar() {
    BarUtils.setNavBarVisibility(window, false)
    BarUtils.setStatusBarVisibility(window, false)
}

fun View.paddingStatusBar() {
    ViewCompat.setOnApplyWindowInsetsListener(
        this
    ) { view: View, windowInsetsCompat: WindowInsetsCompat ->
        view.setPadding(
            0,
            windowInsetsCompat.getInsets(WindowInsetsCompat.Type.statusBars()).top,
            0,
            0
        )
        windowInsetsCompat
    }
}





