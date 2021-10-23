package com.yunfeng.module_base.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.yunfeng.lib.glide.GlideApp;
import com.yunfeng.module_base.R;

import java.lang.ref.WeakReference;

public class GlideImageLoader {
    protected static final int DEF_PIC = R.drawable.module_base_default_banner_pic;
    protected static final int DEF_CIRCLE_PIC = R.drawable.module_base_default_avatar;
    private static final String FILE = "file://";

    protected WeakReference<ImageView> mImageView;

    public static GlideImageLoader create(ImageView imageView) {
        return new GlideImageLoader(imageView);
    }

    protected GlideImageLoader(ImageView imageView) {
        mImageView = new WeakReference<>(imageView);
    }

    public ImageView getImageView() {
        if (mImageView != null) {
            return mImageView.get();
        }
        return null;
    }

    public Context getContext() {
        if (getImageView() != null) {
            return getImageView().getContext().getApplicationContext();
        }
        return null;
    }

    // 加载网络图片
    public void loadImage(String url) {
        loadImage(url, DEF_PIC);
    }

    public void loadImage(String url, int placeholderResId) {
        load(url, requestOptions(placeholderResId));
    }

    // 加载网络圆形图片
    public void loadCircleImage(String url) {
        loadCircleImage(url, DEF_CIRCLE_PIC);
    }

    public void loadCircleImage(String url, int placeholderResId) {
        load(url, circleRequestOptions(placeholderResId, placeholderResId));
    }

    // 加载本地文件图片
    public void loadLocalImage(String localPath) {
        loadLocalImage(localPath, DEF_PIC);
    }

    public void loadLocalImage(String localPath, int placeholderResId) {
        load(FILE + localPath, requestOptions(placeholderResId));
    }

    // 加载本地文件圆形图片
    public void loadLocalCircleImage(String localPath) {
        loadLocalCircleImage(localPath, DEF_CIRCLE_PIC);
    }

    public void loadLocalCircleImage(String localPath, int placeholderResId) {
        load(FILE + localPath, circleRequestOptions(placeholderResId, placeholderResId));
    }

    // 加载图片
    public void load(String url, RequestOptions options) {
        if (url == null || getContext() == null) {
            return;
        }
        requestBuilder(url, options).into(getImageView());
    }


    public RequestOptions requestOptions(int placeholderResId) {
        return requestOptions(placeholderResId, placeholderResId);
    }

    public RequestOptions requestOptions(int placeholderResId, int errorResId) {
        return new RequestOptions()
                .placeholder(placeholderResId)
                .error(errorResId);
    }

    public RequestOptions circleRequestOptions(int placeholderResId, int errorResId) {
        return requestOptions(placeholderResId, errorResId)
                .circleCrop();
    }

    public RequestBuilder<Drawable> requestBuilder(Object obj, RequestOptions options) {
        return GlideApp.with(getContext())
                .load(obj)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                });
    }

}
