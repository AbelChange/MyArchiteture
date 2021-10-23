package com.yunfeng.module_base.service

import android.content.Context
import com.yunfeng.module_base.dto.LoginBaseInfo

interface IAccountService {
    fun isLogin(): Boolean
    fun isDm(): Boolean
    fun isSetPwd(): Boolean
    fun isCertify(): Boolean
    fun isAdult(): Boolean
    fun isNewUser(): Boolean
    fun startLogin(context: Context?)
    fun getLoginToken(): String?
    fun registerObserver(observer: Observer?)
    fun unregisterObserver(observer: Observer?)
    fun notifyLoginSuccess()
    fun notifyLoginCancel()
    fun notifyLoginFailure()
    fun notifyLogout()
    fun getLoginInfo(): LoginBaseInfo?
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