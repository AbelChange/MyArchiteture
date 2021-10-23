package com.yunfeng.module_login.server.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by lianpengcheng on 2019/2/15.
 */
data class LoginPwdReq(
    @SerializedName("mobile") val mobile: String,
    @SerializedName("pwd") val password: String
)