package com.ablec.lib;

import androidx.multidex.MultiDexApplication
import com.blankj.utilcode.util.ProcessUtils
import com.kingja.loadsir.core.LoadSir


/**
 * @Description:    对activity进行适配
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/29 15:22
 */
abstract class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        dependModulesInit()
    }

    companion object {
        lateinit var instance: BaseApplication
            private set
    }



    /**
     * 依赖组件初始化
     */
    abstract fun dependModulesInit()
}