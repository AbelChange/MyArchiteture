package com.ablec.myarchitecture.hook;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookAms {

    public static final String TAG = HookAms.class.getSimpleName();

    public static void hookAMSAidl() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            hookIActivityTaskManager();
        } else {
            hookIActivityManager();
        }
    }

    public static void hookIActivityTaskManager() {
        try {
            Field singletonField = null;
            Class<?> actvityManager = Class.forName("android.app.ActivityTaskManager");
            singletonField = actvityManager.getDeclaredField("IActivityTaskManagerSingleton");
            singletonField.setAccessible(true);
            Object singleton = singletonField.get(null);
            //拿IActivityManager对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            //原始的IActivityTaskManager
            final Object IActivityTaskManager = mInstanceField.get(singleton);

            Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{Class.forName("android.app.IActivityTaskManager")},
                    (proxy, method, args) -> {
                        //                            Log.i(TAG, "invoke: " + method.getName());

                        //偷梁换柱
                        //真正要启动的activity目标
                        Intent raw = null;
                        int index = -1;
                        if ("startActivity".equals(method.getName())) {
                            Log.i(TAG, "invoke: startActivity 启动准备");
                            for (int i = 0; i < args.length; i++) {
                                if (args[i] instanceof Intent) {
                                    raw = (Intent) args[i];
                                    index = i;
                                }
                            }
                            Log.i(TAG, "invoke: raw: " + raw);
                            //代替的Intent
                            Intent newIntent = raw;

                            args[index] = newIntent;

                        }

                        return method.invoke(IActivityTaskManager, args);
                    });

            mInstanceField.set(singleton, proxyInstance);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hookIActivityManager() {

        try {
            Field singletonField;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Class<?> actvityManager = Class.forName("android.app.ActivityManager");
                singletonField = actvityManager.getDeclaredField("IActivityManagerSingleton");
            } else {
                Class<?> actvityManager = Class.forName("android.app.ActivityManagerNative");
                singletonField = actvityManager.getDeclaredField("gDefault");
            }
            singletonField.setAccessible(true);
            Object singleton = singletonField.get(null);

            //拿IActivityManager对象
            Class<?> activityManager = Class.forName("android.util.Singleton");
            Field mInstanceField = activityManager.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            //原始的IActivityManager
            final Object rawIActivityManager = mInstanceField.get(singleton);

            //创建一个IActivityManager代理对象
            Object proxyInstance = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{Class.forName("android.app.IActivityManager")},
                    (proxy, method, args) -> {
                        //偷梁换柱
                        //真正要启动的activity目标
                        Intent raw = null;
                        int index = -1;
                        if ("startActivity".equals(method.getName())) {
                            Log.i(TAG, "invoke: startActivity 启动准备");
                            for (int i = 0; i < args.length; i++) {
                                if (args[i] instanceof Intent) {
                                    raw = (Intent) args[i];
                                    index = i;
                                }
                            }
                            Log.i(TAG, "invoke: raw: " + raw);
                            //代替的Intent
                            Intent newIntent = raw;


                            args[index] = newIntent;

                        }

                        return method.invoke(rawIActivityManager, args);
                    });
            mInstanceField.set(singleton, proxyInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
