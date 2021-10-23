package com.yunfeng.module_login.server.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by lianpengcheng on 2019/2/15.
 */
data class BindMobileReq(
    @SerializedName("from_client")
    var fromClient: Int = ANDROID,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("sms_code") var smsCode: String? = null,
    @SerializedName("bind_type") val bindType: Int? = null,
    @SerializedName("wxcode") val wxcode: String? = null,
    @SerializedName("openid") val openid: String? = null,
    @SerializedName("access_token") val accessToken: String? = null

) : Serializable {
    companion object {
        const val ANDROID = 1;
        const val IOS = 2;
        const val LOGIN_TYPE_WX = 1;
        const val LOGIN_TYPE_QQ = 2;
    }

    constructor(mobile: String?, smsCode: String?, wxCode: String) : this(
        mobile = mobile,
        smsCode = smsCode,
        wxcode = wxCode,
        bindType = LOGIN_TYPE_WX,
    )

    constructor(mobile: String?, smsCode: String?, openId: String?, accessToken: String?) : this(
        mobile = mobile,
        smsCode = smsCode,
        openid = openId,
        accessToken = accessToken,
        bindType = LOGIN_TYPE_QQ,
    )
}