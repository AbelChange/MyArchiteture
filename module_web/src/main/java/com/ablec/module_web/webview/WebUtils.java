package com.ablec.module_web.webview;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.ablec.module_base.scheme.JumpUtils;

public class WebUtils {

    /**
     * 检测是否安装支付宝
     * @return     * @param context
     */
    public static boolean checkAliPayInstalled(Context context){
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName!=null;

    }

    /**
     * 判断是否是支付宝支付
     *
     * @param url
     * @return
     */
    public static boolean parseSchemeZFB(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (url.startsWith("alipays://platformapi/startApp")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否是微信支付
     *
     * @param url
     * @return
     */
    public static boolean parseSchemeWX(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }

        if (url.contains("weixin:")) {
            return true;
        }
        return false;
    }


    /**
     * 判断是否是QQ登陆
     *
     * @param url
     * @return
     */
    public static boolean parseSchemeQQ(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }

        if (url.contains("mqqopensdkapi://")) {
            return true;
        }

        return false;
    }


    /**
     * 判断当前url是否在白名单中
     * @param url                               url
     * @return
     */
    public static boolean isWhiteList(Context context, String url){
        if (parseSchemeZFB(url)) {
            try {
                Intent intent = Intent.parseUri(url,Intent.URI_INTENT_SCHEME);
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.setComponent(null);
                context.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(context, "请检查是否安装支付宝客户端", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        if (parseSchemeWX(url)){
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            } catch (ActivityNotFoundException localActivityNotFoundException) {
                Toast.makeText(context, "请检查是否安装客户端", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        if (parseSchemeQQ(url)){
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            } catch (Exception e) {
                e.printStackTrace();
                // 未安装手Q或安装的版本不支持
                Toast.makeText(context, "未安装手Q或安装的版本不支持", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        //appShcheme,跳转schemeFilter界面，统一处理
        if (JumpUtils.INSTANCE.dealProtocol(context, url)) {
            return true;
        }
        return false;
    }


}
