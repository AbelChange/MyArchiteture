package com.ablec.module_base.config

import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.umeng.socialize.UMShareAPI
import com.ablec.lib.BaseApplication
import com.ablec.module_base.config.ModuleConstant.WX_APP_ID

/**
 * @Description:    三方api
 * @Author:         haoshuaihui
 * @CreateDate:     2021/6/17 16:24
 */
object ThirdApi {

    val wxApi: IWXAPI by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        return@lazy WXAPIFactory.createWXAPI(BaseApplication.instance, WX_APP_ID, true)
    }

    val umengApi: UMShareAPI by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        return@lazy UMShareAPI.get(BaseApplication.instance)
    }
}