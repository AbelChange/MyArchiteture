package com.ablec.module_base.provider

import android.annotation.SuppressLint
import android.content.Context
import androidx.startup.Initializer
import com.ablec.lib.util.MMKVUtil
import com.ablec.module_base.config.ModuleConstant
import com.ablec.module_base.db.AppDatabase
import com.ablec.module_base.service.RouterServiceManager
import com.ablec.module_base.view.loading.EmptyCallback
import com.ablec.module_base.view.loading.ErrorCallback
import com.ablec.module_base.view.loading.LoadingCallback
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.ToastUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.kingja.loadsir.core.LoadSir
import com.sankuai.waimai.router.BuildConfig
import com.sankuai.waimai.router.Router
import com.sankuai.waimai.router.common.DefaultRootUriHandler
import com.sankuai.waimai.router.components.DefaultLogger
import com.sankuai.waimai.router.core.Debugger
import com.sankuai.waimai.router.core.OnCompleteListener
import com.sankuai.waimai.router.core.UriRequest
import com.umeng.commonsdk.UMConfigure

class BaseInitializer : Initializer<Context> {
    override fun create(context: Context): Context {
        GlobalContext = context
        return context
    }

    fun init() {
        if (ProcessUtils.isMainProcess()) {
            configCommonLoading()
            //初始化WMRouter
            initWMRouter(GlobalContext)
            //初始化LiveDataBus
            LiveEventBus.config().setContext(GlobalContext).lifecycleObserverAlwaysActive(false)
                .autoClear(true)
            //初始化 数据库
            AppDatabase.getInstance(GlobalContext)
            MMKVUtil.init(GlobalContext)
            UMConfigure.preInit(
                GlobalContext,
                ModuleConstant.UMENG_APPKEY,
                RouterServiceManager.getAppInfoService()?.channel()
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
    private fun initWMRouter(ctx: Context?) {
        // 创建RootHandler
        val rootHandler = DefaultRootUriHandler(ctx)
        //统一的跳转结果处理，可以在此统计
        rootHandler.globalOnCompleteListener = object : OnCompleteListener {
            override fun onSuccess(request: UriRequest) {}
            override fun onError(request: UriRequest, resultCode: Int) {
                ToastUtils.showShort("Router 失败，request:$request")
            }
        }
        // 初始化
        Router.init(rootHandler)
        // Log开关，建议测试环境下开启，方便排查问题。
        Debugger.setEnableLog(BuildConfig.DEBUG)
        // 调试开关，建议测试环境下开启。调试模式下，严重问题直接抛异常，及时暴漏出来。
        Debugger.setEnableDebug(BuildConfig.DEBUG)
        Debugger.setLogger(DefaultLogger.INSTANCE)
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