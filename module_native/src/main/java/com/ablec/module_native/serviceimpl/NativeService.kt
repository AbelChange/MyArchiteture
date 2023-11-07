package com.ablec.module_native.serviceimpl


import android.content.Context
import com.ablec.module_base.config.Native
import com.ablec.module_base.service.INativeService
import com.ablec.module_native.MainActivity
import com.sankuai.waimai.router.annotation.RouterService


@RouterService(interfaces = [INativeService::class], key = [Native.SERVICE], singleton = true)
class NativeService : INativeService {
    override fun startNativeUi(context: Context) {
        context?.let {
            MainActivity.start(context)
        }
    }
}