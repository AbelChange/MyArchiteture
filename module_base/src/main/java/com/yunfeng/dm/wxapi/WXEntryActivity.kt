package com.yunfeng.dm.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.yunfeng.module_base.config.ThirdApi
import com.yunfeng.module_base.service.RouterServiceManager

/**
 * 只是用来处理登录，分享交给友盟了
 */
class WXEntryActivity : Activity(), IWXAPIEventHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThirdApi.wxApi.handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        ThirdApi.wxApi.handleIntent(intent, this)
    }

    override fun onReq(baseReq: BaseReq) {
    }

    override fun onResp(baseResp: BaseResp?) {
        LogUtils.d("onResp baseResp=" + baseResp!!.errCode + ", " + baseResp.errStr)
        if (baseResp is SendAuth.Resp) { // 微信登录
            if (baseResp.errCode == BaseResp.ErrCode.ERR_OK) {
                // 通知 code
                RouterServiceManager.getAccountService()?.notifyWxCodeGet(baseResp.code)
            }
        }
        finish()
    }
}