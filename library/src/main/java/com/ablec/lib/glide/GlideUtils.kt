package com.ablec.lib.glide

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class GlideUtils {
    companion object {


        /**
         * 只缓存解码数据
         */
        fun preload(
            context: Context,
            url: Any?,
            callBack: () -> Unit = {}
        ) {
            GlideApp.with(context)
                .load(url)
                .priority(Priority.IMMEDIATE)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        callBack.invoke()
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        callBack.invoke()
                        return true
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .preload()
        }

        fun requestOptions(placeholderResId: Int = 0, errorResId: Int = 0): RequestOptions {
            return RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId)
        }

        fun loadImageOption(
            context: Context,
            url: Any?,
            imageView: ImageView, options: RequestOptions
        ) {
            GlideApp.with(context).load(url)
                .apply(options)
                .into(imageView)
        }

        //一般图片
        fun loadImage(
            context: Context,
            url: Any?,
            imageView: ImageView,
            placeholderResourceId: Int = 0,
            errorResourceId: Int = 0,
            width: Int = 0,
            height: Int = 0
        ) {
            loadImageOption(
                context,
                url,
                imageView,
                requestOptions(placeholderResourceId, errorResourceId)
                    .override(width, height)
            )
        }


        //圆形图片设置占位图
        fun loadCircleImage(
            context: Context,
            url: Any?,
            imageView: ImageView,
            placeholderResourceId: Int = 0,
            errorResourceId: Int = 0
        ) {
            loadImageOption(
                context,
                url,
                imageView,
                requestOptions(placeholderResourceId, errorResourceId).circleCrop()
            )
        }

        //圆角图片
        fun loadCornersImage(
            context: Context,
            url: Any?,
            imageView: ImageView,
            radius: Int,
            placeholderResourceId: Int = 0,
            errorResourceId: Int = 0
        ) {
            val roundedCorners = RoundedCorners(radius)
            loadImageOption(
                context, url, imageView,
                requestOptions(placeholderResourceId, errorResourceId)
                    .transform(CenterCrop(), roundedCorners)
            )
        }


        suspend fun clearDiskCache(context: Context) {
            GlideApp.get(context).clearDiskCache()
        }
    }
}