package com.ablechange.learn.design.singleton;

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2023/3/10 17:59
 */
public class Singleton {

    /**
     * 饿汉 线程安全
     */
//    private static Singleton instance=new Singleton();
//    private Singleton(){
//
//    }
//    public static Singleton getInstance(){
//        return instance;
//    }

    /**
     * 懒汉 线程不安全
     */
//    private static Singleton instance;
//    private Singleton() {
//    }
//    public static Singleton getInstance() {
//        if (instance == null) {
//            instance = new Singleton();
//        }
//        return instance;
//    }

    /**
     * 懒汉+同步锁
     */
//    private static Singleton instance;
//    private Singleton() {
//    }
//    public static  synchronized Singleton getInstance() {
//        if (instance == null) {
//            instance = new Singleton();
//        }
//        return instance;
//    }

    /** DCL double check lock
     * voliatale+synchronized
     * 缺点：第一次慢，高并发压力大
     */
//    private static volatile Singleton instance;
//    private Singleton() {
//    }
//    public static  Singleton getInstance() {
//        if (instance == null) {
//            synchronized (Singleton.class){
//                if (instance==null){
//                    instance=new Singleton();
//                }
//            }
//        }
//        return instance;
//    }

    /**
     * 静态内部类单例，最优选择
     */
    private Singleton(){
    }
    public static Singleton getInstance(){
        return SingletonHolder.INSTANCE;
    }
    //只有第一次调用getInstance方法才会加载SingletonHolder类并初始化sInstance（ClassLoader loadClass的sychronized关键字）
    private static class SingletonHolder{
        private static final Singleton INSTANCE = new Singleton();
    }
}
