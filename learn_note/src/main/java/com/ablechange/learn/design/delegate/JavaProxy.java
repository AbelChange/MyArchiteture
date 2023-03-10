package com.ablechange.learn.design.delegate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理主要为了访问控制，与装饰者模式不同（扩展增强）
 * 静态代理 代理关系是确定的 一对一的 扩展性差
 * 动态代理 代理的是 接口类型,扩展性好
 */
public class JavaProxy {

    public static void main(String[] args) {
        new JavaProxy().test();
    }

    private void test() {
        //new StaticProxy().doSth();
        RealSubject realSubject = new RealSubject();
        ISubject subject = getProxy(realSubject);
        subject.doSth();
    }

    private ISubject getProxy(ISubject realSubject) {
        Class<? extends ISubject> clz = realSubject.getClass();
        return (ISubject) Proxy.newProxyInstance(clz.getClassLoader(), clz.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] args) throws Throwable {
                System.out.println("调用方法之前做一些事情！");
                Object object = method.invoke(realSubject, args);
                System.out.println("调用方法之后做一些事情！");
                return object;
            }
        });
    }

    /**
     * 静态代理
     */
    public class StaticProxy implements ISubject {
        public StaticProxy() {
        }

        @Override
        public void doSth() {
            //代理关系是编译前就确定好的
            RealSubject realSubject = new RealSubject();
            realSubject.doSth();
        }
    }


}
