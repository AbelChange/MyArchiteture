package com.yunfeng.module_pay

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/5/19 17:26
 */
class Util {

    companion object {
        fun isAliPayInstalled(context: Context): Boolean {
            val uri = Uri.parse("alipays://platformapi/startApp")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            val componentName = intent.resolveActivity(context.packageManager)
            return componentName != null
        }
    }
}