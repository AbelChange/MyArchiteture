package com.ablec.module_base.provider

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.ablec.module_base.config.ModuleConstant
import com.ablec.module_base.db.AppDatabase
import com.ablec.module_base.util.MMKVUtil
import com.ablec.module_base.view.loading.EmptyCallback
import com.ablec.module_base.view.loading.ErrorCallback
import com.ablec.module_base.view.loading.LoadingCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ProcessUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kingja.loadsir.core.LoadSir
import com.umeng.commonsdk.UMConfigure

class BaseInitializer : Initializer<Context> {
    override fun create(context: Context): Context {
        GlobalContext = context
        init()
        return context
    }

    fun init() {
        if (ProcessUtils.isMainProcess()) {
            configCommonLoading()
            //初始化WMRouter
            initRouter(GlobalContext)
            //初始化LiveDataBus
            LiveEventBus.config().setContext(GlobalContext).lifecycleObserverAlwaysActive(false)
                .autoClear(true)
            //初始化 数据库
            AppDatabase.getInstance(GlobalContext)
            MMKVUtil.init(GlobalContext)
            UMConfigure.preInit(
                GlobalContext,
                ModuleConstant.UMENG_APPKEY,
                "channel"
            )
        }
        LazyInitHolder.init()
    }



    private fun configCommonLoading() {
        LoadSir.beginBuilder()
            .addCallback(LoadingCallback())
            .addCallback(EmptyCallback())
            .addCallback(ErrorCallback())
            //设置默认状态页
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
    }

    /**
     * 初始化路由组件
     */
    private fun initRouter(ctx: Context?) {
        ARouter.init(ctx as? Application)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var GlobalContext: Context
            private set
    }


}