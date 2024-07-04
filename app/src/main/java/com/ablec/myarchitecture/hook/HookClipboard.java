package com.ablec.myarchitecture.hook;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author shuaihui_hao
 * @date 2024/7/2
 * @description
 */
public class HookClipboard {
    private static final String TAG = HookClipboard.class.getSimpleName();

    public static void hookService(Context context) {

        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        try {
            @SuppressLint({"BlockedPrivateApi", "PrivateApi"})
            Field field = Class.forName("android.app.SystemServiceRegistry").getDeclaredField("SYSTEM_SERVICE_FETCHERS");
            field.setAccessible(true);

            ArrayMap objs = (ArrayMap) field.get(null);
            Object fetcher = objs.get("clipboard");
            @SuppressLint("PrivateApi")
            Class<?> clazz = Class.forName("android.app.SystemServiceRegistry$ServiceFetcher");

            objs.put("clipboard", Proxy.newProxyInstance(context.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
                if (method.getName().equals("getService")) {
                    return clipboardManager;
                } else {
                    return method.invoke(fetcher, args);
                }
            }));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        try {
            //通过反射拿到mService字段
            @SuppressLint("SoonBlockedPrivateApi")
            Field mServiceField = ClipboardManager.class.getDeclaredField("mService");
            mServiceField.setAccessible(true);
            Class clazz = Class.forName("android.content.IClipboard");
            //生成代理类
            Object proxyInstance = Proxy.newProxyInstance(context.getClassLoader(), new Class[]{clazz}, new ClipboardHookHandler());
            //将该代理类塞回去
            @SuppressLint("SoonBlockedPrivateApi") Field sServiceField = ClipboardManager.class.getDeclaredField("mService");
            sServiceField.setAccessible(true);
            sServiceField.set(clipboardManager, proxyInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ClipboardHookHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.e(TAG, "clipboardhookhandler invoke");
            String methodName = method.getName();
            int argsLength = args.length;
            //每次从本应用复制的文本，后面都加上分享的出处
            if ("setPrimaryClip".equals(methodName)) {
                if (argsLength >= 2 && args[0] instanceof ClipData) {
                    ClipData data = (ClipData) args[0];
                    String text = data.getItemAt(0).getText().toString();
                    text += "this is shared from ServiceHook-----by Shawn_Dut";
                    args[0] = ClipData.newPlainText(data.getDescription().getLabel(), text);
                }
            }
            return method.invoke(proxy, args);
        }
    }
}
