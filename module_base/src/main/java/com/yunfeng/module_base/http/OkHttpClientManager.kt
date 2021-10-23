package com.yunfeng.module_base.http

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.yunfeng.lib.BaseApplication
import com.yunfeng.lib.http.SSLSocketFactoryCompat
import com.yunfeng.module_base.BuildConfig
import com.yunfeng.module_base.http.intercepter.NetInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/4/29 11:00
 */
class OkHttpClientManager private constructor() {

    companion object {
        private const val DEFAULT_TIME_OUT = 5
        private const val DEFAULT_READ_TIME_OUT = 15

        val instance: OkHttpClient by lazy {
            return@lazy OkHttpClient.Builder().apply {
                if (BuildConfig.FLAVOR != "dmRelease") {
                    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    addInterceptor(ChuckerInterceptor(BaseApplication.instance))
                }
                addInterceptor(NetInterceptor())
                connectTimeout(
                    DEFAULT_TIME_OUT.toLong(),
                    TimeUnit.SECONDS
                )
                readTimeout(
                    DEFAULT_READ_TIME_OUT.toLong(),
                    TimeUnit.SECONDS
                )
                writeTimeout(
                    DEFAULT_READ_TIME_OUT.toLong(),
                    TimeUnit.SECONDS
                )
                retryOnConnectionFailure(false)
                try {
                    // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
                    val trustAllCert: X509TrustManager = object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                    val sslSocketFactory: SSLSocketFactory =
                        SSLSocketFactoryCompat(
                            trustAllCert
                        )
                    sslSocketFactory(sslSocketFactory, trustAllCert)
                } catch (e: Exception) {
                    throw RuntimeException(e)
                }
            }.build()
        }
    }
}