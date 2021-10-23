package com.yunfeng.module_login.server.api

import com.yunfeng.lib.http.base.BaseResp
import com.yunfeng.module_login.server.dto.LoginResp
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 账号信息请求接口
 * Created by lianpengcheng on 2020/8/31.
 */
interface AccountApiService {

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@FieldMap map: Map<String, String>): BaseResp<LoginResp>

    /**
     * 闪验登录
     */
    @FormUrlEncoded
    @POST("login/shanyanLogin")
    suspend fun shanyanLogin(@FieldMap map: Map<String, String>): BaseResp<LoginResp>

    /**
     * 发送验证码
     */
    @FormUrlEncoded
    @POST("login/sendSms")
    suspend fun sendSms(@FieldMap map: Map<String, String>): BaseResp<Void>

    /**
     *验证码登录
     */
    @FormUrlEncoded
    @POST("login/login")
    suspend fun loginByCode(@FieldMap map: Map<String, String>): BaseResp<LoginResp>

    /**
     * 第三方登录
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("login/otherLogin")
    suspend fun thirdLogin(@FieldMap map: Map<String, String>): BaseResp<LoginResp>

    /**
     * 账号密码登录
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("login/loginPwd")
    suspend fun loginPwd(@FieldMap map: Map<String, String>): BaseResp<LoginResp>

    /**
     * 绑定手机号
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("login/bindMobile")
    suspend fun bindMobile(@FieldMap map: Map<String, String>): BaseResp<LoginResp>
}