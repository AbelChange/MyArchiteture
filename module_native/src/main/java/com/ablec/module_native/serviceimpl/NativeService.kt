package com.ablec.module_native.serviceimpl


import android.content.Context
import com.ablec.module_base.config.Native
import com.ablec.module_base.service.INativeService
import com.ablec.module_native.MainActivity
import com.alibaba.android.arouter.facade.annotation.Route


@Route(path = Native.SERVICE, name = "NativeService::class")
class NativeService : INativeService {
    override fun init(context: Context?) {

    }

    override fun startNativeUi(context: Context) {
        context?.let {
            MainActivity.start(context)
        }
    }
}