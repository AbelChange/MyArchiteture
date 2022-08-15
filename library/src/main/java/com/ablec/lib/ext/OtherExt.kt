package com.ablec.lib.ext

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Base64
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import java.io.UnsupportedEncodingException
import java.security.MessageDigest

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/10/21 13:13
 */

/**
 * md5加密
 * @param plaintext 明文
 * @return ciphertext 密文
 */
fun encrypt(plaintext: String): String {
    val bytes = MessageDigest.getInstance("MD5").digest(plaintext.toByteArray())
    return bytes.hex()
}

fun ByteArray.hex(): String {
    return joinToString("") { "%02X".format(it) }
}

/**
 * base64 加密
 */
fun base64(str: String?): String {
    var result = ""
    if (str != null) {
        try {
            result = String(
                Base64.encode(str.toByteArray(charset("utf-8")), Base64.NO_WRAP),
                charset("utf-8")
            )
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }
    return result
}

/**
 * app是否开启定位权限
 */

@SuppressLint("WrongConstant")
fun isLocPermissionEnable(context: Context): Boolean {
    var result1 = -1
    var result2 = -1
    try {
        result1 = PermissionChecker.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        result2 =
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
    } catch (e: Exception) {

    }
    return result1 == 0 || result2 == 0
}

/**
 * 跳转系统位置服务界面
 */

fun Activity.startLocationService() {
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
    startActivity(intent);
}