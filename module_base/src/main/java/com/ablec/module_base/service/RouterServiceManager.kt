package com.ablec.module_base.service

import com.alibaba.android.arouter.launcher.ARouter

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/4/30 13:28
 */
class RouterServiceManager {

    companion object {

        fun getAccountService(): IAccountService? {
            return ARouter.getInstance().navigation(IAccountService::class.java)
        }

        fun getPayService(): IPayService? {
            return ARouter.getInstance().navigation(IPayService::class.java)
        }

        fun getAppInfoService(): IAppInfoService? {
            return ARouter.getInstance().navigation(IAppInfoService::class.java)
        }

        fun getComposeService():IComposeService?{
            return ARouter.getInstance().navigation(IComposeService::class.java)
        }

        fun getNativeService():INativeService?{
            return ARouter.getInstance().navigation(INativeService::class.java)
        }

    }
}