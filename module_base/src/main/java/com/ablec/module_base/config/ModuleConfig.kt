package com.ablec.module_base.config

import com.ablec.lib.BaseApplication
import com.ablec.lib.IBaseModule

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

}