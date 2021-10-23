package com.yunfeng.module_base.glide.imagepicker;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;
import com.yunfeng.lib.glide.GlideApp;
import com.yunfeng.module_base.R;
import com.yunfeng.module_base.glide.GlideImageLoader;

public class ImagePickerLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        GlideImageLoader.create(imageView).loadImage(path, R.drawable.module_base_image_picker_bg);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        GlideImageLoader.create(imageView).loadImage(path, R.drawable.module_base_image_picker_bg);
    }

    @Override
    public void displayImage(Activity activity, Uri uri, ImageView imageView, int width, int height) {
        GlideApp.with(activity).load(uri).placeholder(R.drawable.module_base_image_picker_bg).into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, Uri uri, ImageView imageView, int width, int height) {
        GlideApp.with(activity).load(uri).placeholder(R.drawable.module_base_image_picker_bg).into(imageView);
    }

    @Override
    public void clearMemoryCache() {
    }

}
