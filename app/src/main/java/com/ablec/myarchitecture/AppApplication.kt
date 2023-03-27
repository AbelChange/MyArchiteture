package com.ablec.myarchitecture

//import com.ablec.module_login.config.LoginModule
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.core.view.ViewCompat
import com.ablec.lib.BaseApplication
import com.ablec.lib.util.MMKVUtil
import com.ablec.module_base.provider.BaseInitializer.Companion.GlobalContext
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.ToastUtils
import com.tencent.bugly.crashreport.CrashReport
import dagger.hilt.android.HiltAndroidApp

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/29 14:43
 */
@HiltAndroidApp
class AppApplication : BaseApplication() {

    override fun onCreate() {
        instance = GlobalContext
        super.onCreate()
        //设置系统栏亮色模式与主题一致（系统栏亮色模式则图标深色；系统深色模式则图标亮色）
        registerActivityLifecycleCallbacks(SystemBarActivityLifecycleCallbacks)
        //主进程中初始化
        if (ProcessUtils.isMainProcess()) {
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

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
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
    }
}

object SystemBarActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    private val lightThemeAttributes = intArrayOf(R.attr.isLightTheme)
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        val lightThemeTypedArray: TypedArray = activity.obtainStyledAttributes(lightThemeAttributes)
        //判断当前是否是亮色主题
        val isLightTheme = lightThemeTypedArray.getBoolean(0, true)
        lightThemeTypedArray.recycle()

        ViewCompat.getWindowInsetsController(activity.findViewById(android.R.id.content))?.apply {
            isAppearanceLightStatusBars = isLightTheme
//            isAppearanceLightNavigationBars = isLightTheme
        }
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}
