package com.ablec.myarchitecture

import android.app.Application
import android.graphics.Color
import android.view.Gravity
import androidx.appcompat.app.AppCompatDelegate
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.ToastUtils
import com.tencent.bugly.crashreport.CrashReport
import com.ablec.lib.BaseApplication
import com.ablec.lib.util.MMKVUtil
import com.ablec.module_base.config.BaseModule
import com.ablec.module_base.config.ModuleConfig
import com.ablec.module_base.service.RouterServiceManager
//import com.ablec.module_login.config.LoginModule
import dagger.hilt.android.HiltAndroidApp

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/29 14:43
 */
@HiltAndroidApp
class AppApplication : BaseApplication() {

    /**
     * 需要初始化的模块
     */
    private val moduleClass = listOf(
        BaseModule::class.java,
    )

    override fun onCreate() {
        instance = this
        super.onCreate()
        //主进程中初始化
        if (ProcessUtils.isMainProcess()) {
            //不使用夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            // initToast()
            syncServerConfig()
        }
    }

    private fun syncServerConfig() {
    }

    private fun initBugly(app: Application) {
        // 获取当前包名
        val packageName = app.packageName
        // 获取当前进程名
        val processName: String = ProcessUtils.getCurrentProcessName()
        val strategy = CrashReport.UserStrategy(app)
        strategy.isUploadProcess = processName == packageName
        strategy.appVersion = AppUtils.getAppVersionName(packageName)
//            strategy.appChannel =
        strategy.deviceID = AppUtils.getAppUid().toString()
        // 初始化Bugly
        // CrashReport.initCrashReport(
        //     app,
        //     BUGLY_APPID, BuildConfig.DEBUG, strategy
        // )
    }

    private fun initToast() {
        ToastUtils.getDefaultMaker().setGravity(Gravity.CENTER, 0, 0)
        // ToastUtils.getDefaultMaker().setBgResource(R.drawable.round_corner_toast)
        ToastUtils.getDefaultMaker().setTextColor(Color.WHITE)
    }

    override fun dependModulesInit() {
        ModuleConfig.init(this, moduleClass)
        lazyInit()
    }

    companion object {
        lateinit var instance: AppApplication
            private set
    }

    /**
     * 在同意用户协议之后再进行的初始化
     */
    fun lazyInit() {
        if (!MMKVUtil.getBoolean(MMKVUtil.CHECK_USER_AGREEMENT_MAIN_PROTOCOL_PRIVACY)) {
            return
        }
        if (!BuildConfig.DEBUG) {
            initBugly(this)
        }
        ModuleConfig.lazyInit(this, moduleClass)
    }
}
