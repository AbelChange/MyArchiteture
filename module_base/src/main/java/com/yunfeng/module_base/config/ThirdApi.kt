package com.yunfeng.module_base.config

import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tencent.tauth.Tencent
import com.umeng.socialize.UMShareAPI
import com.yunfeng.lib.BaseApplication
import com.yunfeng.module_base.config.ModuleConstant.QQ_APP_ID
import com.yunfeng.module_base.config.ModuleConstant.WX_APP_ID

/**
 * @Description:    三方api
 * @Author:         haoshuaihui
 * @CreateDate:     2021/6/17 16:24
 */
object ThirdApi {

    val wxApi: IWXAPI by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        return@lazy WXAPIFactory.createWXAPI(BaseApplication.instance, WX_APP_ID, true)
    }

    val qqApi: Tencent by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        return@lazy Tencent.createInstance(
            QQ_APP_ID,
            BaseApplication.instance,
            "${BaseApplication.instance.packageName}.fileprovider"
        )
    }

    val umengApi: UMShareAPI by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        return@lazy UMShareAPI.get(BaseApplication.instance)
    }
}