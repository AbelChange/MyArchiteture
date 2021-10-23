package com.yunfeng.module_login.server.repository

import com.yunfeng.lib.http.convert
import com.yunfeng.module_base.http.RetrofitServiceManager
import com.yunfeng.module_base.http.handleHttpResp
import com.yunfeng.module_login.BuildConfig
import com.yunfeng.module_login.server.api.AccountApiService
import com.yunfeng.module_login.server.dto.BindMobileReq
import com.yunfeng.module_login.server.dto.LoginByCodeReq
import com.yunfeng.module_login.server.dto.LoginPwdReq
import com.yunfeng.module_login.server.dto.SendSmsCodeReq
import com.yunfeng.module_login.server.dto.ShanyanLoginReq
import com.yunfeng.module_login.server.dto.ThirdLoginReq

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/29 19:07
 */
class AccountRepository {

    /**
     * 闪验登录
     */
    suspend fun shanyanLogin(req: ShanyanLoginReq) =
        handleHttpResp {
            loginApiService.shanyanLogin(req.convert())
        }

    /**
     * 发送验证码
     */
    suspend fun sendSmsCode(req: SendSmsCodeReq) =
        handleHttpResp {
            loginApiService.sendSms(req.convert())
        }

    /**
     * 验证码登录
     */
    suspend fun loginByCode(req: LoginByCodeReq) =
        handleHttpResp {
            loginApiService.loginByCode(req.convert())
        }

    /**
     * 验证码登录
     */
    suspend fun thirdLogin(req: ThirdLoginReq) =
        handleHttpResp {
            loginApiService.thirdLogin(req.convert())
        }

    /**
     * 密码登录
     */
    suspend fun loginByPsw(req: LoginPwdReq) =
        handleHttpResp {
            loginApiService.loginPwd(req.convert())
        }

    /**
     * 密码登录
     */
    suspend fun bindMobile(req: BindMobileReq) =
        handleHttpResp {
            loginApiService.bindMobile(req.convert())
        }

    companion object {
        private val loginApiService: AccountApiService
            get() = RetrofitServiceManager.getApiService(
                AccountApiService::class.java,
                BuildConfig.BASE_URL
            )
    }
}