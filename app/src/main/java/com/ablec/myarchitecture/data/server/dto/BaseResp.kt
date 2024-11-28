package com.ablec.myarchitecture.data.server.dto

import com.ablec.module_base.http.ApiResp
import com.google.gson.annotations.SerializedName

class BaseResp<T>(
    @SerializedName("error_code") override var code: Int,
    @SerializedName("reason", alternate = ["message"]) override var message: String? = null,
    @SerializedName("result") override var data: T,
) :ApiResp<T>{
    override val success: Boolean
        get() = code == 0

}


data class PageData<T>(
    @SerializedName("list")
    var list: List<T> = listOf(),

    @SerializedName("pageNum")
    var pageNum: Int = 0,

    @SerializedName("pageSize")
    var pageSize: Int = 0,

    @SerializedName("total")
    val total: Int? = 0
)