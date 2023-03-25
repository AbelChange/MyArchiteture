package com.ablec.myarchitecture.aidl;
import com.ablec.myarchitecture.aidl.IRemoteCallBack;
interface IRemote {

    String plus(int a,int b); //同步调用，如果server方法耗时，可能会anr,

    oneway void async(int a);//oneway(单向调用) 异步 + 回调,保证客户端安全， 虽然是异步的，但是能保证顺序
                              //每个 IBinder 对象都有一个要调度的单向事务队列，保证调用顺序

    void transferFile(in ParcelFileDescriptor pfd);//接收文件

    void registerCallBack(IRemoteCallBack callback);

    void unregisterCallBack(IRemoteCallBack callback);

}