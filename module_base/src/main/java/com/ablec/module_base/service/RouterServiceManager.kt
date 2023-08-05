package com.ablec.module_base.service

import com.ablec.module_base.config.Compose.COMPOSE_SERVICE
import com.sankuai.waimai.router.Router
import com.ablec.module_base.config.Login.ACCOUNT_SERVICE
import com.ablec.module_base.config.Main.APP_INFO_SERVICE
import com.ablec.module_base.config.Pay.PAY_SERVICE

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

        fun getComposeService():IComposeService?{
            return Router.getService(IComposeService::class.java, COMPOSE_SERVICE)
        }

    }
}