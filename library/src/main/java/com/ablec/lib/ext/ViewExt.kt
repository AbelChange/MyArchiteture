package com.ablec.lib.ext

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import kotlin.properties.Delegates


/*--------------------View start---------------*/
fun View.debounceClick(
    windowDuration: Long = 800,
    listener: View.OnClickListener
) {
    var clickTime by Delegates.observable(0L) { pre, old, new ->
        if (new - old >= windowDuration) {
            listener.onClick(this)
        }
    }
    setOnClickListener {
        clickTime = System.currentTimeMillis()
    }
}


/**
 * 扩大点击区域，适用于 view 相对“孤立”的case
 */
fun View.increaseClickArea(
    left: Int = 0,
    right: Int = 0,
    top: Int = 0,
    bottom: Int = 0,
) {
    val delegateArea = Rect()
    val delegate: View = this
    delegate.getHitRect(delegateArea)
    delegateArea.top -= top.dp
    delegateArea.bottom += bottom.dp
    delegateArea.left -= left.dp
    delegateArea.right += right.dp
    if (delegate.parent is View) {
        val touchDelegate = TouchDelegate(delegateArea, delegate);
        delegate.touchDelegate = touchDelegate
    }
}

/**
 * 获取viewId对应的字符串
 */
fun View.getIdName(): String? {
    return try {
        resources.getResourceEntryName(this.id)
    } catch (e: Exception) {
        null // 防止 NotFoundException，返回 null 以避免崩溃
    }
}















