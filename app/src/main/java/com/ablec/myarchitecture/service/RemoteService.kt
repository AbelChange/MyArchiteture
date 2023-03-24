package com.ablec.myarchitecture.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.os.RemoteException
import com.ablec.myarchitecture.aidl.IRemote
import com.ablec.myarchitecture.aidl.IRemoteCallBack
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.*


class RemoteService : Service() {

    private val TAG = "RemoteService"

    private val remoteCallBackList: RemoteCallbackList<IRemoteCallBack> =
        RemoteCallbackList<IRemoteCallBack>()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main + CoroutineExceptionHandler { _, throwable ->
        LogUtils.e(
            TAG,
            throwable
        )
    })

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }


    private val binder = object : IRemote.Stub() {
        override fun plus(a: Int, b: Int): String {
            //子线程
            val result = a + b
            val name = Thread.currentThread().name
            return "binder线程:${name},result:$result"
        }

        override fun async(a: Int) {
            //子线程
            val name = Thread.currentThread().name
            broadcast("binder线程:${name},回调结果${a}")
        }

        private fun broadcast(s: String) {
            var i: Int = remoteCallBackList.beginBroadcast()
            while (i > 0) {
                i--
                try {
                    remoteCallBackList.getBroadcastItem(i).onSuccess(s)
                } catch (e: RemoteException) {
                    // The RemoteCallbackList will take care of removing
                    // the dead object for us.
                }
            }
            remoteCallBackList.finishBroadcast()
        }

        override fun registerCallBack(callback: IRemoteCallBack?) {
            remoteCallBackList.register(callback)
        }

        override fun unregisterCallBack(callback: IRemoteCallBack?) {
            remoteCallBackList.unregister(callback)
        }
    }

    companion object {
        val ACTION_PLUS = "intent.action.ACTION_PLUS"
    }
}