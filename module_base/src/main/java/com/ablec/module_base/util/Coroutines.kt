package com.ablec.module_base.util

import com.blankj.utilcode.util.ApiUtils.Api
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


interface SuspendResponse<R> {
    fun onSuccess(result: R)
    fun onFail(t: Throwable);
}

@JvmOverloads
fun <R> getContinuation(
    response: SuspendResponse<R>,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
): Continuation<R> {
    return object : Continuation<R> {
        override val context: CoroutineContext
            get() = dispatcher

        override fun resumeWith(result: Result<R>) {
            result.onSuccess {
                response.onSuccess(it)
            }.onFailure {
                response.onFail(it)
            }
        }
    }
}

object Coroutines {

    var myCallBack:CallBack?= null

    @JvmStatic
    fun bridge2Suspend(arg: String): String {
        return runBlocking { callBackToSuspend(arg) }
    }

    //回调转suspend函数
    private suspend fun callBackToSuspend(arg: String) = suspendCancellableCoroutine<String> {

        try {
            registerCallBack(object :CallBack{
                override fun onSuccess(result: String) {
                    it.resume(":1")
                }
                ˆ
            })
            //call back style
        }catch (e:Exception){
            it.resumeWithException(e)
        }
    }

    private fun registerCallBack(cb:CallBack){
        thread {
            try {
                Thread.sleep(2000)
                cb.onSuccess("registerCallBack")
            }catch (e:Exception){
                cb.onFail(e)
            }
        }.start()
    }
}




interface CallBack{
    fun onSuccess(result: String)
    fun onFail(e: Exception)
}
