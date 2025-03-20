package com.ablec.lib.ext

import android.content.Context
import android.content.res.Resources
import android.widget.Toast
import androidx.annotation.AnyRes
import com.blankj.utilcode.util.ToastUtils

fun Context.showToast(stringResId: Int, duration: Int = Toast.LENGTH_SHORT) {
    if (duration == Toast.LENGTH_SHORT) {
        ToastUtils.showShort(stringResId)
    } else {
        ToastUtils.showLong(stringResId)
    }
}

fun Context.showToast(content: String? = null, duration: Int = Toast.LENGTH_SHORT) {
    if (duration == Toast.LENGTH_SHORT) {
        ToastUtils.showShort(content)
    } else {
        ToastUtils.showLong(content)
    }
}

/**
 * 获取资源id对应的名称
 */
fun Context.getResourceEntryName(@AnyRes resId: Int): String? {
    return try {
        resources.getResourceEntryName(resId)
    } catch (e: Resources.NotFoundException) {
        null // 资源 ID 无效时返回 null
    }
}