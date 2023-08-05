package com.ablec.module_login.serviceimpl

import android.content.Context
import com.sankuai.waimai.router.annotation.RouterService
import com.ablec.module_base.config.Login.ACCOUNT_SERVICE
import com.ablec.module_base.service.IAccountService
import com.blankj.utilcode.util.LogUtils

/**
 * @Description:
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/30 13:24
 */
@RouterService(interfaces = [IAccountService::class], key = [ACCOUNT_SERVICE], singleton = true)
class AccountService : IAccountService {
    private val mObservers: MutableList<IAccountService.Observer> = ArrayList()
    override fun isLogin(): Boolean {
        return false
    }

    // 使用注解声明该方法是一个Provider
    override fun startLogin(context: Context?) {
        LogUtils.d("吊起登陆")
    }

    override fun registerObserver(observer: IAccountService.Observer?) {
        if (observer != null && !mObservers.contains(observer)) {
            mObservers.add(observer)
        }
    }

    override fun unregisterObserver(observer: IAccountService.Observer?) {
        if (observer != null) {
            mObservers.remove(observer)
        }
    }

    override fun notifyLoginSuccess() {
        val observers = observers
        for (i in observers.indices.reversed()) {
            observers[i].onLoginSuccess()
        }
    }

    override fun notifyLoginCancel() {
        val observers = observers
        for (i in observers.indices.reversed()) {
            observers[i].onLoginCancel()
        }
    }

    override fun notifyLoginFailure() {
        val observers = observers
        for (i in observers.indices.reversed()) {
            observers[i].onLoginFailure()
        }
    }

    override fun notifyLogout() {
        val observers = observers
        for (i in observers.indices.reversed()) {
            observers[i].onLogout()
        }
    }

    override fun notifyLogoff() {
        val observers = observers
        for (i in observers.indices.reversed()) {
            observers[i].onLogoff()
        }
    }

    override fun notifyWxCodeGet(code: String?) {

    }

    override fun getUserId(): String? {
        return ""
    }

    override fun logout() {

    }

    override fun localLogoff(context: Context) {
    }

    private val observers: Array<IAccountService.Observer>
        get() = mObservers.toTypedArray()

    /**
     * 获取登录token
     */
    override fun getLoginToken(): String? {
        return ""
    }

}