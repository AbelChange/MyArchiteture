package com.temption.myarchitecture.glide

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.integration.webp.decoder.WebpDrawable
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

object GlideUtils {
    //一般图片
    fun loadImage(context: Context,
                  url: String,
                  imageView: ImageView,
                  placeholderResourceId: Int = 0,
                  errorResourceId: Int = 0) {
        GlideApp.with(context).load(url)
            .placeholder(placeholderResourceId)
            .error(errorResourceId)
            .into(imageView)
    }

    fun loadWebP(context: Context, res: Any, imageview: ImageView,
                 isSkipMemory: Boolean) {
        GlideApp.with(context)
            .load(res)
            .skipMemoryCache(isSkipMemory)
            .optionalTransform(
                WebpDrawable::class.java,
                WebpDrawableTransformation(CircleCrop())
            )
            .into(imageview)
    }

    //圆形图片设置占位图
    fun loadCircleImage(context: Context,
                        url: String,
                        imageView: ImageView,
                        placeholderResourceId: Int = 0,
                        errorResourceId: Int = 0) {
        GlideApp.with(context).load(url)
            .circleCrop()
            .placeholder(placeholderResourceId)
            .error(errorResourceId)
            .into(imageView)
    }

    //圆角图片
    fun loadCornersImage(context: Context,
                         url: String,
                         imageView: ImageView,
                         radius: Int,
                         placeholderResourceId: Int = 0,
                         errorResourceId: Int = 0) {
        val roundedCorners = RoundedCorners(radius)
        GlideApp.with(context).load(url)
            .centerCrop()
            .placeholder(placeholderResourceId)
            .error(errorResourceId)
            .apply(RequestOptions.bitmapTransform(roundedCorners))
            .into(imageView)
    }
}