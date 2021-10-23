package com.yunfeng.module_base.service

import com.sankuai.waimai.router.Router
import com.yunfeng.module_base.config.Login.ACCOUNT_SERVICE
import com.yunfeng.module_base.config.Main.APP_INFO_SERVICE
import com.yunfeng.module_base.config.Pay.PAY_SERVICE
import com.yunfeng.module_base.config.Web.WEB_SERVICE

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/4/30 13:28
 */
class RouterServiceManager {

    companion object {
        fun getAccountService(): IAccountService? {
            return Router.getService(IAccountService::class.java, ACCOUNT_SERVICE)
        }

        fun getPayService(): IPayService? {
            return Router.getService(IPayService::class.java, PAY_SERVICE)
        }

        fun getAppInfoService(): IAppInfoService? {
            return Router.getService(IAppInfoService::class.java, APP_INFO_SERVICE)
        }

        fun getWebService(): IWebService? {
            return Router.getService(IWebService::class.java, WEB_SERVICE)
        }

        fun getPushService(): IPushService? {
            return Router.getService(IPushService::class.java, WEB_SERVICE)
        }
    }
}