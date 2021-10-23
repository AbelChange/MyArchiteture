package com.yunfeng.module_login.data

import android.text.TextUtils
import com.yunfeng.lib.util.MMKVUtil
import com.yunfeng.module_base.dto.LoginBaseInfo
import com.yunfeng.module_login.config.LoginConstant
import com.yunfeng.module_login.server.dto.LoginResp

/**
 * 账号数据存储
 * Created by lianpengcheng on 2020/9/2.
 */
object AccountDataStore {

    private var mLoginInfo: LoginResp? = null

    init {
        mLoginInfo = MMKVUtil.getParcelable(MMKVUtil.KEY_ACCOUNT_INFO, LoginResp::class.java)
    }

    fun isLogin(): Boolean {
        return !TextUtils.isEmpty(mLoginInfo?.token)
    }

    fun isDm(): Boolean {
        return mLoginInfo?.loginInfo?.isDm == LoginConstant.Bool.YES
    }

    fun isSetPwd(): Boolean {
        return mLoginInfo?.isSetPwd == LoginConstant.Bool.YES
    }

    fun isCertify(): Boolean {
        return mLoginInfo?.loginInfo?.isCertify == LoginConstant.Bool.YES
    }

    fun isAdult(): Boolean {
        return mLoginInfo?.loginInfo?.isAdult == LoginConstant.Bool.YES
    }

    fun isNewUser(): Boolean {
        return mLoginInfo?.isNewUser == LoginConstant.Bool.YES
    }

    fun getToken(): String? {
        return mLoginInfo?.token
    }

    fun getLoginInfo(): LoginBaseInfo? {
        return mLoginInfo?.loginInfo
    }

    fun getLoginResp(): LoginResp? {
        return mLoginInfo
    }

    fun setLoginInfo(loginInfo: LoginResp?) {
        mLoginInfo = loginInfo
        MMKVUtil.putParcelable(MMKVUtil.KEY_ACCOUNT_INFO, loginInfo)
    }

    ///登出
    fun logout() {
        MMKVUtil.putParcelable(MMKVUtil.KEY_ACCOUNT_INFO, null)
        mLoginInfo = null
    }

    fun getUserId(): String? {
        return mLoginInfo?.loginInfo?.userId
    }
}