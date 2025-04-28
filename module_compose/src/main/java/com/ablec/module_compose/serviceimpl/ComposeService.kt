package com.ablec.module_compose.serviceimpl


import android.content.Context
import com.ablec.module_base.config.Compose.COMPOSE_SERVICE
import com.ablec.module_base.service.IComposeService
import com.ablec.module_compose.MainActivity
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = COMPOSE_SERVICE, name = "ComposeService::class")
class ComposeService : IComposeService {
    override fun init(context: Context?) {

    }

    override fun start(context: Context?) {
        context?.let {
            MainActivity.start(context)
        }
    }

    override fun ss() {
    }
}