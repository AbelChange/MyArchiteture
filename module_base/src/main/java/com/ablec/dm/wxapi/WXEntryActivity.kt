package com.ablec.dm.wxapi

import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.umeng.socialize.weixin.view.WXCallbackActivity
import com.ablec.module_base.config.ThirdApi

/**
 * 只是用来处理登录，分享交给友盟了
 */
class WXEntryActivity : WXCallbackActivity(), IWXAPIEventHandler {

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

    override fun onResp(resp: BaseResp?) {
        LogUtils.d("onResp baseResp=" + resp!!.errCode + ", " + resp.errStr)
        //自己处理登录
        if (resp is SendAuth.Resp) {
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                // code获取成功
//                LiveEventBus.get<String>(LOGIN_WX_CODE_GET).post(resp.code)
            } else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                ToastUtils.showShort("登录取消")
            }
            finish()
        } else {
            //友盟处理分享
            super.onResp(resp)
            return
        }
    }


}