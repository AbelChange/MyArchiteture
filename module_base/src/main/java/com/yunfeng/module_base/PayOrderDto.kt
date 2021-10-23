package com.yunfeng.module_base

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/5/19 15:23
 */
@Keep
data class PayOrderDto(
    val alipayStr: String? = null,
    @SerializedName("appid")
    val appId: String? = null,
    val noncestr: String? = null,
    @SerializedName("package")
    val packageInfo: String? = null,
    val partnerid: String? = null,
    val prepayid: String? = null,
    val sign: String? = null,
    val timestamp: String? = null,
)