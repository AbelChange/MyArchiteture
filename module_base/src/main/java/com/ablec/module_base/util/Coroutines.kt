package com.ablec.module_base.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext


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
    val scope = CoroutineScope(SupervisorJob())
    @JvmStatic
    suspend fun javaCallSuspend(arg: String): String {
        delay(2000)
        return "callSuspendSuccess"
    }


}