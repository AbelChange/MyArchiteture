package com.yunfeng.module_login.server.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 *
 * Created by yangrong on 2021/5/25.
 */
@Keep
data class SendSmsCodeReq(@SerializedName("mobile") val mobile: String)