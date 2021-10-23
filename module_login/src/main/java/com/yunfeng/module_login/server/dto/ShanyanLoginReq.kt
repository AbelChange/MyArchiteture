package com.yunfeng.module_login.server.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 *
 * Created by yangrong on 2021/5/24.
 */
@Keep
data class ShanyanLoginReq(@SerializedName("token") val token: String)
