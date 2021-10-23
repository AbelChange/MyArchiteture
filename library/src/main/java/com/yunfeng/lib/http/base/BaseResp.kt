package com.yunfeng.lib.http.base

import com.google.gson.annotations.SerializedName
import com.yunfeng.lib.http.base.ResponseStatus.Companion.SUCCESS
import java.io.Serializable

/**
 * 服务器返回数据传输对象
 */

open class BaseResp<T>(
    @SerializedName("code", alternate = ["error_code"]) var code: Int? = null,
    @SerializedName("error_msg", alternate = ["reason"]) var msg: String? = null,
    @SerializedName("data", alternate = arrayOf("result")) var `data`: T? = null,
) {
    fun isSuccess(): Boolean = code == SUCCESS
}

interface ResponseStatus {
    companion object {
        /**
         * 请求成功
         */
        const val SUCCESS = 200
    }
}

data class BaseFailedResp<T>(val errorCode: Int?, val errorMsg: String?) :
    BaseResp<T>(code = errorCode, msg = errorMsg, data = null)

class PageData<T> : Serializable {
    @SerializedName("rows", alternate = ["list", "data"])
    val list: List<T> = listOf()

    /**
     * 总页数
     */
    @SerializedName("last_page")
    val totalPage: Int = 0

    /**
     * 每页数据量
     */
    @SerializedName("per_page")
    val pageSize: Int = 0

    /**
     * 当前第几页
     */
    @SerializedName("current_page")
    val pageNo: Int = 0

    /**
     * 总数
     */
    @SerializedName("total")
    val total: Int = 0
}