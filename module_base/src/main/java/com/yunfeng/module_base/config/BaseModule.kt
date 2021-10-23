package com.yunfeng.module_base.config

import android.app.Application
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.ToastUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.sankuai.waimai.router.BuildConfig
import com.sankuai.waimai.router.Router
import com.sankuai.waimai.router.common.DefaultRootUriHandler
import com.sankuai.waimai.router.components.DefaultLogger
import com.sankuai.waimai.router.core.Debugger
import com.sankuai.waimai.router.core.OnCompleteListener
import com.sankuai.waimai.router.core.UriRequest
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig
import com.yunfeng.lib.BaseApplication
import com.yunfeng.lib.IBaseModule
import com.yunfeng.lib.util.MMKVUtil
import com.yunfeng.module_base.config.ModuleConstant.QQ_APP_SECRET
import com.yunfeng.module_base.db.AppDatabase.Companion.getInstance
import com.yunfeng.module_base.service.RouterServiceManager

/**
 * 组件通信模块初始化
 * @author haoshuaihui
 */
class BaseModule : IBaseModule {
    override fun init(application: BaseApplication?) {
        if (ProcessUtils.isMainProcess()) {
            application?.let {
                //初始化WMRouter
                initWMRouter(it)
                //初始化LiveDataBus
                initLiveDataBus(it)
                //初始化 数据库
                initDb(it)
                MMKVUtil.init(it)
                UMConfigure.preInit(
                    application,
                    ModuleConstant.UMENG_APPKEY,
                    RouterServiceManager.getAppInfoService()?.channel()
                )
            }
        }
    }

    private fun initDb(application: Application?) {
        getInstance(application!!)
    }

    /**
     * 初始化路由组件
     */
    private fun initWMRouter(application: Application?) {
        // 创建RootHandler
        val rootHandler = DefaultRootUriHandler(application)
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

    private fun initUmeng(application: BaseApplication?) {
        UMConfigure.init(
            application,
            ModuleConstant.UMENG_APPKEY,
            RouterServiceManager.getAppInfoService()?.channel(),
            UMConfigure.DEVICE_TYPE_PHONE,
            null
        )
        UMConfigure.setLogEnabled(BuildConfig.DEBUG)
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
        PlatformConfig.setWeixin(ModuleConstant.WX_APP_ID, "")
        PlatformConfig.setQQZone(ModuleConstant.QQ_APP_ID, QQ_APP_SECRET)
        PlatformConfig.setQQFileProvider("com.yunfeng.dm.fileprovider")
        PlatformConfig.setWXFileProvider("com.yunfeng.dm.fileprovider")
    }

    /**
     * 初始化LiveDataBus事件总线
     */
    private fun initLiveDataBus(application: Application?) {
        LiveEventBus.config().setContext(application).lifecycleObserverAlwaysActive(false)
            .autoClear(true)
    }

    override fun lazyInit(application: BaseApplication?) {
        if (ProcessUtils.isMainProcess()) {
            initUmeng(application)
        }
    }
}