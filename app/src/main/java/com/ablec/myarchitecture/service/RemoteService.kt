package com.ablec.myarchitecture.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.ParcelFileDescriptor
import android.os.RemoteCallbackList
import android.os.RemoteException
import com.ablec.myarchitecture.aidl.IRemote
import com.ablec.myarchitecture.aidl.IRemoteCallBack
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader


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


    private val binder = object : IRemote.Stub() {
        override fun plus(a: Int, b: Int): String {
            //binder线程池子线程 需要考虑线程安全问题
            val pid = android.os.Process.myPid()
            val name = Thread.currentThread().name
            return "pid:$pid,binder线程:${name},result:$a + b"
        }

        override fun async(a: Int) {
            //binder线程池子线程 需要考虑线程安全问题
            val name = Thread.currentThread().name
            broadcast("binder线程:${name},回调结果${a}")
        }

        override fun transferFile(pfd: ParcelFileDescriptor) {
            val inputStream = FileInputStream(pfd.fileDescriptor)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val lines = mutableListOf<String>()
            try {
                while (true) {
                    val line = bufferedReader.readLine() ?: break
                    lines.add(line)
                }
                bufferedReader.close()
                inputStream.close()
                pfd.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            LogUtils.d(TAG,lines.toString())
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