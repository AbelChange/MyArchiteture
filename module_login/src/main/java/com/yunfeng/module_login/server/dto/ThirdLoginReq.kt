package com.yunfeng.module_login.server.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by lianpengcheng on 2019/2/15.
 */
@Parcelize
data class ThirdLoginReq(
    @SerializedName("bind_type")
    var bindType: Int = 0,
    @SerializedName("unionid")
    var unionId: String?,
    @SerializedName("openid")
    var openId: String?,
    @SerializedName("access_token")
    var accessToken: String?,
    @SerializedName("wxcode")
    var wxCode: String? = null,
) : Parcelable {
    constructor(openId: String?, accessToken: String?) : this(
        LOGIN_TYPE_QQ,
        null,
        openId,
        accessToken,
        null
    )

    constructor(wxCode: String?) : this(LOGIN_TYPE_WX, null, null, null, wxCode)

    companion object {
        const val LOGIN_TYPE_WX = 1;
        const val LOGIN_TYPE_QQ = 2;
    }
}