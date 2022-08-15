package com.ablec.module_base.http



import kotlin.coroutines.cancellation.CancellationException


/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/30 14:03
 */
suspend fun <T> handleHttpResp(
    action: suspend () -> BaseResp<T>
): BaseResp<T> {
    return try {
        val invoke = action.invoke()
        handleHttpSuccess(invoke)
    } catch (it: Throwable) {
        if (it is CancellationException) {
            throw it
        } else {
            handleHttpError(it)
        }
    }
}

/**
 * http
 */
private fun <T> handleHttpError(e: Throwable): BaseFailedResp<T> {
    val apiException = ExceptionEngine.handleException(e)
    return BaseFailedResp(apiException.code, apiException.message)
}

/**
 * 返回200，但是还要判断isSuccess
 */
private fun <T> handleHttpSuccess(data: BaseResp<T>): BaseResp<T> {
    return if (data.isSuccess) {
        data
    } else {
        //逻辑异常
        if (data.invalidToken){
            //处理登出

        }
        BaseFailedResp(data.code, data.msg)
    }
}


