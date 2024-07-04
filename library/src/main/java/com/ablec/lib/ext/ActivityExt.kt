package com.ablec.lib.ext

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
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
fun Activity.immerse(darkFont: Boolean) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = Color.TRANSPARENT
    window.navigationBarColor = Color.TRANSPARENT
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        window.isNavigationBarContrastEnforced = false
        window.isStatusBarContrastEnforced = false
    }
//    val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
//    windowInsetsController.isAppearanceLightStatusBars = darkFont
//    windowInsetsController.isAppearanceLightNavigationBars = darkFont
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

fun View.replaceStatusBar() {
    ViewCompat.setOnApplyWindowInsetsListener(
        this
    ) { view: View, windowInsetsCompat: WindowInsetsCompat ->
        view.layoutParams.height =
            windowInsetsCompat.getInsets(WindowInsetsCompat.Type.statusBars()).top
        windowInsetsCompat
    }
}


fun View.paddingNavigationBar() {
    ViewCompat.setOnApplyWindowInsetsListener(
        this
    ) { view: View, windowInsetsCompat: WindowInsetsCompat ->
        view.setPadding(
            0,
            0,
            0,
            windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom)
        windowInsetsCompat
    }
}




