package com.yunfeng.lib.glide

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import cc.shinichi.library.glide.progress.ProgressManager
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.yunfeng.lib.http.SSLSocketFactoryCompat
import okhttp3.OkHttpClient
import java.io.InputStream
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

/**
 * 绑定OKhttp3  设置缓存信息等
 */
@GlideModule
class ImageGlideModule : AppGlideModule() {

    @SuppressLint("CheckResult")
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // 100 MB
        val diskCacheSizeBytes = 100 * 1024 * 1024
        builder.setDiskCache(
            InternalCacheDiskCacheFactory(
                context,
                CACHE_DIR,
                diskCacheSizeBytes.toLong()
            )
        )
        val calculator = MemorySizeCalculator.Builder(context)
            .setMemoryCacheScreens(2f)
            .build()
        builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))
        builder.setBitmapPool(LruBitmapPool(calculator.bitmapPoolSize.toLong()))

        //设置全局选项
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            val requestOptions = RequestOptions()
            requestOptions.format(DecodeFormat.PREFER_RGB_565)
            builder.setDefaultRequestOptions(requestOptions)
        }
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.replace(
            GlideUrl::class.java, InputStream::class.java,
            OkHttpUrlLoader.Factory(glideInstance)
        )
    }

    val glideInstance: OkHttpClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        OkHttpClient.Builder()
            .addNetworkInterceptor(ProgressManager.getNetWorkInterceptor())
            .retryOnConnectionFailure(false)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .hostnameVerifier { _, _ -> return@hostnameVerifier true }
            .run {
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

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    companion object {
        const val CACHE_DIR = "img_cache" //缓存目录
    }
}