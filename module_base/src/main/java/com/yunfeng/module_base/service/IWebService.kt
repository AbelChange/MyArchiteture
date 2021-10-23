package com.yunfeng.module_base.service

import android.content.Context

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/5/19 14:03
 */
interface IWebService {

    fun startWeb(context: Context, url: String? = null)

    fun invokeJsByName(jsFunc: String?)

    fun startGroupOrderDetail(context: Context, it: String)
}