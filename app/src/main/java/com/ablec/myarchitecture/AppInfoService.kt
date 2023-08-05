package com.ablec.myarchitecture

import com.ablec.module_base.config.Main.APP_INFO_SERVICE
import com.ablec.module_base.service.IAppInfoService
import com.sankuai.waimai.router.annotation.RouterService

@RouterService(interfaces = [IAppInfoService::class], key = [APP_INFO_SERVICE], singleton = true)
class AppInfoService : IAppInfoService {
    override fun channel(): String? {
        return "channel"
    }
}