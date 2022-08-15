package com.ablec.module_base.http


import com.ablec.module_base.http.ResponseStatus.Companion.LOGIN_EXPIRED
import com.ablec.module_base.http.ResponseStatus.Companion.SUCCESS
import com.ablec.module_base.util.toJson
import com.google.gson.annotations.SerializedName



open class BaseResp<T>(
    @SerializedName("code", alternate = ["error_code","errorCode"]) var code: Int? = null,
    @SerializedName("message", alternate = ["reason"]) var msg: String? = null,
    @SerializedName("data", alternate = ["result"]) var `data`: T? = null,
) {
    val isSuccess: Boolean
        get() = code == SUCCESS

    val invalidToken: Boolean
        get() = code == LOGIN_EXPIRED

    override fun toString(): String {
        return this.toJson()
    }

    fun onSuccess(action: (T?) -> Unit): BaseResp<T> {
        if (isSuccess) {
            action.invoke(data)
        }
        return this
    }

    fun onError(action: (String?) -> Unit): BaseResp<T> {
        if (!isSuccess) {
            action.invoke(msg)
        }
        return this
    }

}

interface ResponseStatus {
    companion object {
        /**
         * 请求成功
         */
        const val SUCCESS = 0

        const val LOGIN_EXPIRED = 201

        const val MONEY_NOT_ENOUGH = 20019

        const val PK_NOT_FINISH = 20304

        const val THIRD_NEED_BIND = 20311

    }
}

data class BaseFailedResp<T>(val errorCode: Int?, val errorMsg: String?) :
    BaseResp<T>(code = errorCode, msg = errorMsg, data = null)

class PageData<T> {
    @SerializedName("list", alternate = ["rows"])
    val list: List<T> = listOf()

    @SerializedName("hasNext")
    val hasNext: Boolean = false

}