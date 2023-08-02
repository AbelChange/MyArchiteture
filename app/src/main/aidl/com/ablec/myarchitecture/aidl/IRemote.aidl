package com.ablec.myarchitecture.aidl;
import com.ablec.myarchitecture.aidl.IRemoteCallBack;
interface IRemote {

    String plus(int a,int b); //同步调用，如果server方法耗时，可能会anr,

    oneway void async(int a);//oneway(单向调用) 不阻塞调用方 避免引入out

    void transferFile(in ParcelFileDescriptor pfd);//接收文件

    void registerCallBack(IRemoteCallBack callback);

    void unregisterCallBack(IRemoteCallBack callback);

}