package com.ablec.lib.ext

import android.app.Activity
import android.graphics.Color
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import com.blankj.utilcode.util.BarUtils

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/29 13:28
 */

//沉浸 + 透明状态栏
fun Activity.setImmerse(blackFont: Boolean) {
    BarUtils.setStatusBarLightMode(this, blackFont)
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.statusBarColor = Color.TRANSPARENT
    window.navigationBarColor = Color.BLACK
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//        window.navigationBarDividerColor = Color.TRANSPARENT
//    }
    //设置沉浸后专栏栏和导航字体的颜色
    ViewCompat.getWindowInsetsController(window.decorView)
        ?.let { controller ->
            controller.isAppearanceLightStatusBars = blackFont
            controller.isAppearanceLightNavigationBars = blackFont
        }
}