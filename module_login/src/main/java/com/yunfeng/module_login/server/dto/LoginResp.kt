package com.yunfeng.module_login.server.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.yunfeng.module_base.dto.LoginBaseInfo
import kotlinx.parcelize.Parcelize

/**
 *
 * Created by yangrong on 2021/5/24.
 */
@Parcelize
data class LoginResp(
    @SerializedName("is_bind_mobile") val isBindPhone: Int = 0,
    @SerializedName("setPwdToken") val setPwdToken: String?,
    @SerializedName("loginToken") val token: String?,
    @SerializedName("is_setpwd") val isSetPwd: String?,
    @SerializedName("userinfo") val loginInfo: LoginBaseInfo?,
    @SerializedName("is_new_user") val isNewUser: String?,
) : Parcelable {
    fun needBindPhone(): Boolean {
        return isBindPhone == 1
    }
}