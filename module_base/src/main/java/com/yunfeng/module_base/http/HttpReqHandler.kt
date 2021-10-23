package com.yunfeng.module_base.http

import com.yunfeng.lib.http.base.BaseFailedResp
import com.yunfeng.lib.http.base.BaseResp
import com.yunfeng.lib.http.exception.ExceptionEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/30 14:03
 */
suspend fun <T> handleHttpResp(
    action: suspend () -> BaseResp<T>
): BaseResp<T> {
    return withContext(Dispatchers.IO) {
        try {
            val invoke = action.invoke()
            handleHttpSuccess(invoke)
        } catch (e: Exception) {
            handleHttpError<T>(e)
        }
    }
}

/**
 * 非后台返回错误，捕获到的异常
 */
private fun <T> handleHttpError(e: Throwable): BaseFailedResp<T> {
    val apiException = ExceptionEngine.handleException(e)
    //401处理
    return BaseFailedResp(apiException.code, apiException.message)
}

/**
 * 返回200，但是还要判断isSuccess
 */
private fun <T> handleHttpSuccess(data: BaseResp<T>): BaseResp<T> {
    return if (data.isSuccess()) {
        data
    } else {
        //逻辑异常
        BaseFailedResp(data.code, data.msg)
    }
}


