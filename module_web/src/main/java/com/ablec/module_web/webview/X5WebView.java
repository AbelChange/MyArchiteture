package com.ablec.module_web.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;

public class X5WebView extends WebView {

    private Context mContext;

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        mContext = arg0;
        initWebViewSettings();
        this.getView().setClickable(true);
    }

    public X5WebView(Context arg0) {
        super(arg0);
//        setBackgroundColor(85621);
    }

    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setAppCachePath(mContext.getDir("appcache", 0).getPath());
        // 设置允许访问文件数据
        webSetting.setAllowFileAccess(false);
        webSetting.setDomStorageEnabled(true);
        // 设置支持javascript
        webSetting.setJavaScriptEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSetting.setMediaPlaybackRequiresUserGesture(false);
        //设置图片自动加载
        webSetting.setLoadsImagesAutomatically(true);
        // 手机修改字体大小后，页面字体大小不变
        webSetting.setTextZoom(100);
        webSetting.setMediaPlaybackRequiresUserGesture(false);

        webSetting.setUseWideViewPort(true);
        webSetting.setLoadWithOverviewMode(true);

        webSetting.setAppCachePath(mContext.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(mContext.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(mContext.getDir("geolocation", 0)
                .getPath());
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);

        // 自适应屏幕
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setDefaultTextEncodingName("utf-8");

        //extension
        if (getX5WebViewExtension() != null) {
            this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        }

    }

}
