package com.ablec.module_base.provider

import android.content.Context
import com.ablec.module_base.BuildConfig
import com.ablec.module_base.config.ModuleConstant
import com.ablec.module_base.provider.BaseInitializer.Companion.GlobalContext
import com.ablec.module_base.service.RouterServiceManager
import com.ablec.module_base.util.MMKVUtil
import com.ablec.module_base.web.X5WebViewInitializer
import com.blankj.utilcode.util.ProcessUtils
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig

object LazyInitHolder {
    fun init(){
        if (!MMKVUtil.getBoolean(MMKVUtil.CHECK_USER_AGREEMENT_MAIN_PROTOCOL_PRIVACY)) {
            return
        }
        if (ProcessUtils.isMainProcess()) {
            initUmeng(GlobalContext)
            X5WebViewInitializer.init(GlobalContext)
        }
    }

    private fun initUmeng(ctx: Context?) {
        UMConfigure.init(
            ctx,
            ModuleConstant.UMENG_APPKEY,
            RouterServiceManager.getAppInfoService()?.channel(),
            UMConfigure.DEVICE_TYPE_PHONE,
            null
        )
        UMConfigure.setLogEnabled(BuildConfig.DEBUG)
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
        PlatformConfig.setWeixin(ModuleConstant.WX_APP_ID, "")
        PlatformConfig.setWXFileProvider("com.ablec.dm.fileprovider")
    }
}