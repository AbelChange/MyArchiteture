package com.ablec.lib.ext

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

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
fun Activity.immerse(darkFont: Boolean) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = Color.TRANSPARENT
    window.navigationBarColor = Color.TRANSPARENT
    WindowInsetsControllerCompat(this.window, this.window.decorView).apply {
        isAppearanceLightStatusBars = darkFont
        isAppearanceLightNavigationBars = darkFont
    }
}

fun Activity.hideSystemBar() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowCompat.getInsetsController(window,window.decorView).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
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





