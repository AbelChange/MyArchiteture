package com.ablec.myarchitecture.hook;

import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookNotification {

    static final String TAG = HookNotification.class.getName();

    public static void hookNms(final Context context) throws Exception {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Method method = NotificationManager.class.getDeclaredMethod("getService");
            method.setAccessible(true);
            //得到object
            final Object sService = method.invoke(notificationManager);
            //代理对象
            Class clazz = Class.forName("android.app.INotificationManager");
            Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{clazz},
                    new NotificationHookHandler(sService));
            //代理对象替换之前
            Field sServiceField = NotificationManager.class.getDeclaredField("sService");
            sServiceField.setAccessible(true);
            sServiceField.set(notificationManager, proxyInstance);
        } catch (Exception e) {
            Log.e(TAG, "hookNotificationManager: failed" + e);
        }

    }


    public static class NotificationHookHandler implements InvocationHandler {
        Object rawService;

        public NotificationHookHandler(Object rawService) {
            this.rawService = rawService;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String name = method.getName();
            if (args != null && args.length > 0) {
                for (Object arg : args) {
                    Log.d(TAG, "invoke: arg=" + arg);
                }
            }
            // 操作交由 rawService 处理，不拦截通知
            return method.invoke(rawService, args);
        }
    }
}
