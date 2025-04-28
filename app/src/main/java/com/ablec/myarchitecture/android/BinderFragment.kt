package com.ablec.myarchitecture.android

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.ablec.lib.ext.debounceClick
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.IRemote
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.FragmentSimpleTextBinding
import com.ablec.myarchitecture.service.ICalculator
import java.io.File
import java.io.IOException


/**
 * aidl客户端，同步调用与异步调用
 */
class BinderFragment : Fragment(R.layout.fragment_simple_text) {

    companion object {
        const val TAG = "BinderFragment"
    }

    private val bindings: FragmentSimpleTextBinding by viewBinding()

    private var binder: IRemote? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindings.startBind.setOnClickListener {
            bindService()
        }

        bindings.startCall.setOnClickListener {
//            sendFile("")
//            shareMem("")
//            val aidlData = AidlData()
//            binder?.receive(aidlData);
//            LogUtils.d(aidlData.toString())
        }

        //手写aidl
        bindings.startBindCustom.debounceClick {
            val intent = Intent().apply {
                component = ComponentName(
                    "com.ablec.myarchitecture",
                    "com.ablec.myarchitecture.service.CalculatorService"
                )
            }
            requireActivity().bindService(intent, object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    service?.let {
                        val calculator: ICalculator = object : ICalculator.Stub.Proxy(it) {
                            override fun add(a: Int, b: Int): Int {
                                val data1 = Parcel.obtain()
                                data1.writeInt(a)
                                data1.writeInt(b)
                                val reply1 = Parcel.obtain()
                                service.transact(1, data1, reply1, 0)
                                return reply1.readInt()
                            }

                            override fun minus(a: Int, b: Int): Int {
                                val data1 = Parcel.obtain()
                                data1.writeInt(a)
                                data1.writeInt(b)
                                val reply1 = Parcel.obtain()
                                service.transact(2, data1, reply1, 0)
                                return reply1.readInt()
                            }
                        }
                        Log.d(TAG, "自定义binder调用" + calculator.minus(1, 4))
                    }

                }

                override fun onServiceDisconnected(name: ComponentName?) {

                }
            }, Context.BIND_AUTO_CREATE)
        }
    }

    private val serviceConnection = object : ServiceConnection {
        //sercvice: 远程服务返回的stub对象
        override fun onServiceConnected(cpname: ComponentName?, service: IBinder?) {
            Log.d(TAG, service.toString())
            //参数 binder驱动提供的binder对象
            //如果本地找到，说明在同一个进程，该binder不需要代理
            //没找到，明是远程对象，需要代理 这里IBinder对象是BinderProxy
            binder = IRemote.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder = null
        }
    }

    private fun simpleCall() {
        binder?.let {
            val result = it.plus(1, 2)
            bindings.textView.text = "获取同步计算结果$result"
        }
    }

    private fun bindService() {
        val intent = Intent().apply {
            component = ComponentName(
                "com.ablec.myarchitecture",
                "com.ablec.myarchitecture.service.RemoteService"
            )
            action = "ACTION_PLUS"
        }
        requireActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

    }

//    private var callBack = object : IRemoteCallBack.Stub() {
//        override fun onSuccess(result: String?) {
//            bindings.textView.text = "异步回调$result"
//        }
//
//        override fun onFail(error: String?) {
//            bindings.textView.text = error
//        }
//    }


    private fun asyncCall() {
        binder?.let {
            try {
//                it.registerCallBack(callBack)
                for (i in 1 until 1000) {
                    it.async(i)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //文件共享
    private fun sendFile(path: String) {
        try {
            val pfd = ParcelFileDescriptor.open(
                File(path),
                ParcelFileDescriptor.MODE_READ_ONLY
            )
            binder?.transferFile(pfd)
            pfd.close()
        } catch (e: RemoteException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    //内存共享:匿名共享内存
    private fun shareMem(path: String) {
        try {
            // 创建 MemoryFile，并写入数据
            val bytes = "Hello, World!".toByteArray()
            val memoryFile = MemoryFile("MemoryFileDemo", bytes.size)
            memoryFile.writeBytes(bytes, 0, 0, bytes.size)
            val pfd = memoryFile.fileDescriptor()
            binder?.transferFile(pfd)
            memoryFile.close()
            pfd?.close()
        } catch (e: RemoteException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
//            binder?.unregisterCallBack(callBack)
            requireActivity().unbindService(serviceConnection)
        } catch (_: Exception) {

        }
    }

}