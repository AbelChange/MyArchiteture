package com.ablec.module_base.http

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/30 14:03
 */
interface ApiResp<T> {
    val message: String?
    val data: T
    val code: Int?
    val success: Boolean
}

data class ApiException(override val message: String?, override val cause: Throwable?) :
    Exception(message, cause)

suspend inline fun <T> handleApiCall(crossinline call: suspend () -> ApiResp<T>): Result<T> {
    return try {
        val response = call.invoke()
        if (response.success) {
            Result.success(response.data)
        } else {
            Result.failure(ApiException(response.message, null))
        }
    } catch (e: Exception) {
        Result.failure(ApiException("网络错误:${e.message}", e))
    }
}


