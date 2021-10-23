package com.yunfeng.module_base.dto.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TimeReq(
    @SerializedName(("drama_id"))
    val dramaId: String? = null,
    @SerializedName(("day"))
    val day: String? = null
) : Serializable