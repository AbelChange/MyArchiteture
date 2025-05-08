package com.ablec.myarchitecture

import android.content.Context
import com.ablec.module_base.config.Login.ACCOUNT_SERVICE
import com.ablec.module_base.config.Main.APP_INFO_SERVICE
import com.ablec.module_base.service.IAppInfoService
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = APP_INFO_SERVICE, name = "AppInfoService::class")
class AppInfoService : IAppInfoService {
    override fun init(context: Context?) {

    }

    override fun channel(): String? {
        return "channel"
    }
}