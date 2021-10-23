package com.yunfeng.lib

import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.ProcessUtils
import com.kingja.loadsir.core.LoadSir
import com.yunfeng.lib.view.loading.EmptyCallback
import com.yunfeng.lib.view.loading.ErrorCallback
import com.yunfeng.lib.view.loading.LoadingCallback

/**
 * @Description:    对activity进行适配
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/29 15:22
 */
abstract class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (ProcessUtils.isMainProcess()) {
            configCommonLoading()
        }
        dependModulesInit()
    }

    companion object {
        lateinit var instance: BaseApplication
            private set
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
     * 依赖组件初始化
     */
    abstract fun dependModulesInit()
}