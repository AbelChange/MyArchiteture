package com.ablec.module_base.util

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object CoroutinesBridge {

    /**
     * java同步 call suspend
     * 这里会同步阻塞调用方，需要调用方做额外处理
     */
    @JvmStatic
    fun bridge2Suspend(arg: String): String {
        return runBlocking { callBackToSuspend(arg) }
    }

    /**
     * callback -> suspend
     */
    private suspend fun callBackToSuspend(arg: String) = suspendCancellableCoroutine {
        it.invokeOnCancellation {
            //对取消的收尾响应
        }
        Api.javaCallback(object : Api.CallBack {
            override fun onSuccess(result: String) {
                it.resume(":1")
            }

            override fun onFail(e: Exception) {
                it.resumeWithException(e)
            }
        })
    }

}

object Api {

    interface CallBack {
        fun onSuccess(result: String)
        fun onFail(e: Exception)
    }

    fun javaCallback(cb: CallBack) {
        thread {
            try {
                Thread.sleep(2000)
                cb.onSuccess("registerCallBack")
            } catch (e: Exception) {
                cb.onFail(e)
            }
        }.start()
    }
}







