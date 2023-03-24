// IRemoteCallBack.aidl
package com.ablec.myarchitecture.aidl;

interface IRemoteCallBack {
    void onSuccess(String result);
    void onFail(String error);
}