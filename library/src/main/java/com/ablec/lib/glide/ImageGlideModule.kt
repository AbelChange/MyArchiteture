package com.ablec.lib.glide

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

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

    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    companion object {
        const val CACHE_DIR = "img_cache" //缓存目录
    }
}