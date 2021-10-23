package com.yunfeng.module_login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.yunfeng.lib.http.base.BaseResp
import com.yunfeng.module_base.service.RouterServiceManager
import com.yunfeng.module_login.server.dto.BindMobileReq
import com.yunfeng.module_login.server.dto.LoginByCodeReq
import com.yunfeng.module_login.server.dto.LoginPwdReq
import com.yunfeng.module_login.server.dto.LoginResp
import com.yunfeng.module_login.server.dto.SendSmsCodeReq
import com.yunfeng.module_login.server.dto.ShanyanLoginReq
import com.yunfeng.module_login.server.dto.ThirdLoginReq
import com.yunfeng.module_login.server.repository.AccountRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val mRepository = AccountRepository()

    val accountService = RouterServiceManager.getAccountService()

    /**
     * 修改此值切换 账号密码登录/验证码登录
     */
    private val _loginMode = MutableLiveData<Boolean>()

    init {
        _loginMode.value = false
    }

    val loginModeExpose: LiveData<Boolean> get() = _loginMode

    fun shanyanLogin(req: ShanyanLoginReq): LiveData<BaseResp<LoginResp>> {
        return liveData { emit(mRepository.shanyanLogin(req)) }
    }

    fun sendSmsCode(req: SendSmsCodeReq): LiveData<BaseResp<Void>> {
        return liveData { emit(mRepository.sendSmsCode(req)) }
    }

    var mobile: String? = null

    fun switchLoginMode() {
        _loginMode.value?.let {
            _loginMode.value = !it
        } ?: kotlin.run {
            _loginMode.value = false
        }
    }

    fun loginByCode(req: LoginByCodeReq): LiveData<BaseResp<LoginResp>> {
        return liveData { emit(mRepository.loginByCode(req)) }
    }

    /**
     * 三方登录
     */
    fun thirdLogin(req: ThirdLoginReq): LiveData<BaseResp<LoginResp>> {
        return liveData { emit(mRepository.thirdLogin(req)) }
    }

    /**
     *密码登录
     */
    fun loginByPsw(mobile: String, psw: String): LiveData<BaseResp<LoginResp>> {
        return liveData { emit(mRepository.loginByPsw(LoginPwdReq(mobile, psw))) }
    }

    /**
     *绑定手机号
     */
    fun bindMobile(req: BindMobileReq): LiveData<BaseResp<LoginResp>> {
        return liveData { emit(mRepository.bindMobile(req)) }
    }
}

