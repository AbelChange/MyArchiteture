package com.yunfeng.module_base.http.intercepter

import com.yunfeng.module_base.http.BaseHeader
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class NetInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val rawRequest: Request = chain.request()
        val newHeader = BaseHeader.getHeader()
        val request = rawRequest.newBuilder()
            .addHeader("Connection", "close")
            .removeHeader(JSONPARAMS)
            .addHeader(JSONPARAMS, newHeader)
            .build()
        return chain.proceed(request)
    }

    companion object {
        private const val JSONPARAMS = "JSONPARAMS"
    }
}