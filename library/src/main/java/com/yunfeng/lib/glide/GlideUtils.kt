package com.yunfeng.lib.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Priority
import com.bumptech.glide.integration.webp.decoder.WebpDrawable
import com.bumptech.glide.integration.webp.decoder.WebpDrawableTransformation
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import java.io.File

class GlideUtils {
    companion object {

        fun getCacheDir(context: Context): File? {
            return GlideApp.getPhotoCacheDir(context)
        }

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

        fun loadWebP(
            context: Context, res: Any?, imageview: ImageView,
            isSkipMemory: Boolean = false
        ) {
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

        fun getBitmapAsyc(context: Context, url: String?, callBack: (Bitmap?) -> Unit) {
            GlideApp.with(context).asBitmap()
                .load(url)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        callBack.invoke(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        callBack.invoke(null)
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        callBack.invoke(null)
                    }
                })
        }

        fun getDrawable(context: Context, o: Any?, callBack: (Drawable?) -> Unit) {
            GlideApp.with(context).asDrawable()
                .load(o)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        callBack.invoke(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        callBack.invoke(null)
                    }
                })
        }

        /**
         * 在列表里面使用需要注意异步带来的问题
         */
        fun startWebPOnce(view: ImageView, drawable: Int, callBack: (drawable: Drawable?) -> Unit) {
            val rawDrawable = view.drawable
            getDrawable(view.context, drawable) {
                if (it is WebpDrawable) {
                    it.loopCount = 1
                    it.registerAnimationCallback(object :
                        Animatable2Compat.AnimationCallback() {
                        override fun onAnimationEnd(drawable: Drawable?) {
                            super.onAnimationEnd(drawable)
                            //这里重新置
                            view.setImageDrawable(rawDrawable)
                            callBack.invoke(drawable)
                            it.unregisterAnimationCallback(this)
                        }

                        override fun onAnimationStart(drawable: Drawable?) {
                            super.onAnimationStart(drawable)
                        }
                    })
                    view.setImageDrawable(it)
                    it.startFromFirstFrame()
                }
            }
        }

        suspend fun clearDiskCache(context: Context) {
            GlideApp.get(context).clearDiskCache()
        }
    }
}