package com.ablec.module_native

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ablec.module_native.databinding.ModuleNativeActivityMainBinding
import com.blankj.utilcode.util.ToastUtils

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ModuleNativeActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ModuleNativeActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnJNICallBack.setOnClickListener {
            NativeLib.setJavaCallback(object : CallBack {
                override fun onStringGet(str: String) {
                    ToastUtils.showShort(str)
                }
            })
        }
        binding.btnGetStringFromJni.setOnClickListener {
            ToastUtils.showShort(NativeLib.stringFromJNI())
        }

    }


    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }
}