package com.yunfeng.module_web.config

import android.os.Build
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ProcessUtils
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.QbSdk.PreInitCallback
import com.tencent.smtt.sdk.WebView
import com.yunfeng.lib.BaseApplication
import com.yunfeng.lib.IBaseModule


/**
 * 组件通信模块初始化
 * @author haoshuaihui
 */
class WebModule : IBaseModule {

    val TAG = "WebModule"

    override fun init(application: BaseApplication?) {
        //用户同意隐私政策之前禁止调用api
        QbSdk.disableSensitiveApi()
    }

    /**
     * 接入TBS后会收集用户用户信息
     * https://x5.tencent.com/docs/questions.html
     */
    override fun lazyInit(application: BaseApplication?) {
        if(!ProcessUtils.isMainProcess()){
            return
        }
        QbSdk.disableSensitiveApi()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            if (!ProcessUtils.isMainProcess()) {
                WebView.setDataDirectorySuffix(ProcessUtils.getCurrentProcessName());
            }
        }
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)

        val cb: PreInitCallback = object : PreInitCallback {
            override fun onViewInitFinished(success: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.d(TAG,"X5 加载结果:$success,version:${QbSdk.getTbsVersion(application)}")
            }

            override fun onCoreInitFinished() {
            }
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(application, cb)
        val strategy = UserStrategy(application)
        strategy.setCrashHandleCallback(object : CrashReport.CrashHandleCallback() {
            override fun onCrashHandleStart(
                p0: Int,
                p1: String?,
                p2: String?,
                p3: String?
            ): MutableMap<String, String> {
                val map = LinkedHashMap<String, String>()
                val x5CrashInfo = WebView.getCrashExtraMessage(application)
                map["x5crashInfo"] = x5CrashInfo
                return map
            }

            override fun onCrashHandleStart2GetExtraDatas(
                p0: Int,
                p1: String?,
                p2: String?,
                p3: String?
            ): ByteArray {
                try {
                    return "Extra data.".toByteArray(charset("UTF-8"))
                } catch (e: Exception) {
                    LogUtils.e(TAG,e)
                }
                return super.onCrashHandleStart2GetExtraDatas(p0, p1, p2, p3)
            }

        })
    }
}