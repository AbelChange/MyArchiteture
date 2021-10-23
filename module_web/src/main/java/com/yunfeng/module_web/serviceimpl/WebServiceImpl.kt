package com.yunfeng.module_web.serviceimpl

import android.content.Context
import com.jeremyliao.liveeventbus.LiveEventBus
import com.sankuai.waimai.router.annotation.RouterService
import com.yunfeng.module_base.config.ModuleConstant
import com.yunfeng.module_base.config.Web
import com.yunfeng.module_base.service.IWebService
import com.yunfeng.module_base.service.RouterServiceManager
import com.yunfeng.module_web.BuildConfig
import com.yunfeng.module_web.WebActivity

/**
 * @Description: webview service
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/30 13:24
 */
@RouterService(interfaces = [IWebService::class], key = [Web.WEB_SERVICE], singleton = true)
class WebServiceImpl : IWebService {

    override fun startWeb(context: Context, url: String?) {
        url?.let {
            WebActivity.start(context, it)
        }
    }

    override fun invokeJsByName(jsFunc: String?) {
        jsFunc?.let {
            LiveEventBus.get(ModuleConstant.KEY_JS_FUNC, String::class.java).post(jsFunc)
        }
    }

    override fun startGroupOrderDetail(context: Context, it: String) {
        //登录判断
        if (RouterServiceManager.getAccountService()?.isLogin() == false) {
            RouterServiceManager.getAccountService()?.startLogin(context)
            return
        }
        startWeb(context,DRAMA_GROUP_HOLDER + it)
    }

    companion object{
        const val DRAMA_GROUP_HOLDER =  "${BuildConfig.BASE_URL}wpersion/scriptGroupDetail?orderNo="
    }
}