package com.ablec.module_base.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

interface IAccountService : IProvider {
    fun isLogin(): Boolean
    fun startLogin(context: Context?)
    fun getLoginToken(): String?
    fun registerObserver(observer: Observer?)
    fun unregisterObserver(observer: Observer?)
    fun notifyLoginSuccess()
    fun notifyLoginCancel()
    fun notifyLoginFailure()
    fun notifyLogout()
    fun getUserId(): String?
    fun logout()
    fun notifyWxCodeGet(code: String?)
    fun localLogoff(context: Context)
    fun notifyLogoff()

    interface Observer {
        fun onLoginSuccess()
        fun onLoginCancel()
        fun onLoginFailure()
        fun onLogout()
        fun onLogoff()
    }
}