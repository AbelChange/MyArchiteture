package com.ablec.lib;

import android.app.Application


/**
 * @Description:    对activity进行适配
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/29 15:22
 */
abstract class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: BaseApplication
            private set
    }



}