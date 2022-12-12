package com.ablec.lib.glide


import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.io.File

class GlideUtils {
    companion object {

        /**
         * 预加载
         */
        fun preload(
            context: Context,
            url: Any?,
            strategy: DiskCacheStrategy,
            callBack: (Drawable?) -> Unit = {}
        ) {
            GlideApp.with(context)
                .load(url)
                .apply(
                    RequestOptions()
                        .diskCacheStrategy(strategy)
                )
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        callBack.invoke(null)
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        callBack.invoke(resource)
                        return true
                    }
                })
                .preload()
        }

        private fun requestOptions(
            placeholderResId: Int? = null,
            errorResId: Int? = null
        ): RequestOptions {
            val requestOptions = RequestOptions()
            placeholderResId?.let {
                requestOptions.placeholder(placeholderResId)
            }
            errorResId?.let {
                requestOptions.error(errorResId)
            }
            return requestOptions
        }

        private fun loadImageOption(
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
            placeholderResourceId: Int? = null,
            errorResourceId: Int? = placeholderResourceId,
            width: Int = 0,
            height: Int = 0,
            strategy: DiskCacheStrategy? = DiskCacheStrategy.AUTOMATIC,
            animate: Boolean? = true,
            dontTransform: Boolean? = null
        ) {

            var option = requestOptions(placeholderResourceId, errorResourceId)
                .diskCacheStrategy(strategy ?: DiskCacheStrategy.AUTOMATIC)
                .dontTransform()
                .override(width, height)
            // 不做变换 - 避免图片模糊
            if (dontTransform == true) {
                option = option.dontTransform()
            }
            if (animate == false) {
                option = option.dontAnimate()
            }
            loadImageOption(
                context,
                url,
                imageView,
                option
            )
        }

        fun clearDiskCache(context: Context) {
            GlideApp.get(context).clearDiskCache()
        }

        fun getCacheDir(context: Context): File? {
            return GlideApp.getPhotoCacheDir(context)
        }
    }
}