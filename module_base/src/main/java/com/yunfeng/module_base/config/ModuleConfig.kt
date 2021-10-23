package com.yunfeng.module_base.config

import com.yunfeng.lib.BaseApplication
import com.yunfeng.lib.IBaseModule
import com.yunfeng.module_base.BuildConfig

object ModuleConfig {
    fun init(application: BaseApplication, classes: List<Class<out IBaseModule>>?) {
        classes?.let {
            for (cls in it) {
                try {
                    val iBaseModule = cls.newInstance()
                    //调用初始化方法
                    iBaseModule.init(application)
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InstantiationException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun lazyInit(application: BaseApplication, classes: List<Class<out IBaseModule>>?) {
        classes?.let {
            for (cls in it) {
                try {
                    val iBaseModule = cls.newInstance()
                    //调用初始化方法
                    iBaseModule.lazyInit(application)
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: InstantiationException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun isTestMode(): Boolean {
        return "debugService" == BuildConfig.FLAVOR || "intranet" == BuildConfig.FLAVOR || BuildConfig.DEBUG
    }
}