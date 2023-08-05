package com.ablec.module_compose.serviceimpl


import android.content.Context
import com.ablec.module_base.config.Compose.COMPOSE_SERVICE
import com.ablec.module_base.service.IComposeService
import com.ablec.module_compose.MainActivity
import com.sankuai.waimai.router.annotation.RouterService


@RouterService(interfaces = [IComposeService::class], key = [COMPOSE_SERVICE], singleton = true)
class ComposeService : IComposeService {
    override fun start(context: Context?) {
        context?.let {
            MainActivity.start(context)
        }
    }

    override fun ss() {
    }
}