package com.yunfeng.module_base.dto

import com.google.gson.annotations.SerializedName

/**
 * Desc: ***
 * copyright，shfengqu.com
 * Author: XiaoLongCai
 * Date: 2021/6/18 17:22
 * Email: caixiaolong@yungengxin.com
 */
data class LocationDto(
    @SerializedName("cityCode")
    var cityCode: String? = null,
    @SerializedName("latitude")
    var latitude: String? = null,
    @SerializedName("longitude")
    var longitude: String? = null,
    @SerializedName("address")
    var address: String? = null
)