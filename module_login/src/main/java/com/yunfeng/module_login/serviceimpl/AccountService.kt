package com.yunfeng.module_login.serviceimpl

import android.content.Context
import com.jeremyliao.liveeventbus.LiveEventBus
import com.sankuai.waimai.router.Router
import com.sankuai.waimai.router.annotation.RouterService
import com.yunfeng.module_base.config.Login.ACCOUNT_SERVICE
import com.yunfeng.module_base.config.Login.LOGIN_ACTIVITY
import com.yunfeng.module_base.config.Login.SHANYAN_LOGIN_ACTIVITY
import com.yunfeng.module_base.dto.LoginBaseInfo
import com.yunfeng.module_base.service.IAccountService
import com.yunfeng.module_base.service.RouterServiceManager
import com.yunfeng.module_login.config.LoginConstant.Bus.KEY_THIRD_LOGIN
import com.yunfeng.module_login.config.LoginModule
import com.yunfeng.module_login.data.AccountDataStore
import com.yunfeng.module_login.server.dto.ThirdLoginReq
import java.util.*

/**
 * @Description:
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/30 13:24
 */
@RouterService(interfaces = [IAccountService::class], key = [ACCOUNT_SERVICE], singleton = true)
class AccountService : IAccountService {
    private val mObservers: MutableList<IAccountService.Observer> = ArrayList()

    override fun isLogin(): Boolean {
        return AccountDataStore.isLogin()
    }

    override fun isDm(): Boolean {
        return AccountDataStore.isDm()
    }

    override fun isSetPwd(): Boolean {
        return AccountDataStore.isSetPwd()
    }

    override fun isCertify(): Boolean {
        return AccountDataStore.isCertify()
    }

    override fun isAdult(): Boolean {
        return AccountDataStore.isAdult()
    }

    override fun isNewUser(): Boolean {
        return AccountDataStore.isNewUser()
    }

    // 使用注解声明该方法是一个Provider
    override fun startLogin(context: Context?) {
        if (LoginModule.syLoginIsValid) {
            Router.startUri(context, SHANYAN_LOGIN_ACTIVITY)
        } else {
            Router.startUri(context, LOGIN_ACTIVITY)
        }
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
        LiveEventBus.get<ThirdLoginReq>(KEY_THIRD_LOGIN).post(ThirdLoginReq(code))
    }

    private val observers: Array<IAccountService.Observer>
        get() = mObservers.toTypedArray()

    /**
     * 获取登录token
     */
    override fun getLoginToken(): String? {
        return AccountDataStore.getToken()
    }

    /**
     * 获取登录信息
     */
    override fun getLoginInfo(): LoginBaseInfo? {
        return AccountDataStore.getLoginInfo()
    }

    override fun getUserId(): String? {
        return AccountDataStore.getUserId()
    }

    override fun logout() {
        if (isLogin()) {
            AccountDataStore.logout()
            notifyLogout()
        }
    }

    /**
     * 注销页面
     */
    override fun localLogoff(context: Context) {
        AccountDataStore.logout()
        notifyLogoff()
        RouterServiceManager.getAppInfoService()?.startMainActivity(context)
    }
}