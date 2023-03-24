package com.ablec.myarchitecture.android

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.fragment.app.Fragment
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.aidl.IRemote
import com.ablec.myarchitecture.aidl.IRemoteCallBack
import com.ablec.myarchitecture.databinding.FragmentSimpleTextBinding
import com.blankj.utilcode.util.LogUtils

class BinderFragment : Fragment(R.layout.fragment_simple_text) {

    private val bindings: FragmentSimpleTextBinding by viewBinding()

    private var binder:IRemote?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindings.startBind.setOnClickListener {
            bindService()
        }

        bindings.startCall.setOnClickListener {
            startCall()
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(cpname: ComponentName?, service: IBinder?) {
            //主线程
            binder = IRemote.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder = null
        }
    }

    private fun bindService() {
        val intent = Intent().apply {
            component = ComponentName("com.ablec.myarchitecture","com.ablec.myarchitecture.service.RemoteService")
            action = "ACTION_PLUS"
        }
        requireActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

    }

    private var callBack = object :IRemoteCallBack.Stub(){
        override fun onSuccess(result: String?) {
            bindings.textView.text = "异步回调$result"
        }

        override fun onFail(error: String?) {
            bindings.textView.text = error
        }
    }
    private fun startCall() {
//       binder?.let {
//            val result = it.plus(1, 2)
//            bindings.textView.text = "获取同步计算结果$result"
//        }
        binder?.let {
            it.registerCallBack(callBack)
            for (i in 1 until 1000) {
                it.async(i)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        try {
            binder?.unregisterCallBack(callBack)
            requireActivity().unbindService(serviceConnection)
        }catch (_:Exception){

        }
    }

}