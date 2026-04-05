package com.ablec.myarchitecture;
import com.ablec.myarchitecture.data.AidlData;
interface IRemote {

    String plus(int a,int b); //同步调用，如果server方法耗时，可能会anr,

    void send(in AidlData data);//对象类型 in/out/inout，建议标明，默认IN,代表数据流向

    void receive(out AidlData data);

    oneway void async(int a);//oneway(单向调用) 不阻塞调用方 避免引入out //在同一个IBinder对象调用中，会按照调用顺序依次执行

    void transferFile(in ParcelFileDescriptor pfd);//server接收大文件

    void registerCallBack(IRemoteCallBack callback);

    void unregisterCallBack(IRemoteCallBack callback);


    interface IRemoteCallBack {
        void onSuccess(String result);
        void onFail(String error);
    }
}