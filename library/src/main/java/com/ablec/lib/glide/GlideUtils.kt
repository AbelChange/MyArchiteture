package com.ablec.lib.glide


import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.io.File

class GlideUtils {
    companion object {

        private fun buildRequestOption(
            overrideWidth: Int? = null,
            overrideHeight: Int? = null,
            placeholderResId: Int? = null,
            errorResId: Int? = null,
            transformation: Transformation<Bitmap>? = null,
        ): RequestOptions {
            var requestOptions = RequestOptions().dontAnimate().dontTransform()
            placeholderResId?.let {
                requestOptions = requestOptions.placeholder(it)
            }
            errorResId?.let {
                requestOptions = requestOptions.error(it)
            }
            transformation?.let {
                requestOptions = requestOptions.transform(it)
            }
            if (overrideWidth != null && overrideHeight != null) {
                requestOptions = requestOptions.override(
                    overrideWidth,
                    overrideHeight
                )
            }
            return requestOptions
        }

        fun loadImage(
            context: Context,
            url: Any?,
            imageView: ImageView,
            options: RequestOptions = RequestOptions().dontAnimate().dontTransform()
        ) {
            GlideApp.with(context).load(url)
                .apply(options)
                .into(imageView)
        }

        fun clearDiskCache(context: Context) {
            GlideApp.get(context).clearDiskCache()
        }

        fun getCacheDir(context: Context): File? {
            return GlideApp.getPhotoCacheDir(context)
        }
    }
}