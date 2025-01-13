package com.ablec.myarchitecture.logic.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ablec.lib.base.BaseActivity
import com.ablec.lib.ext.hideSystemBar
import com.ablec.lib.ext.immerse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2022/8/16 14:45
 */
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(1000)
                MainActivity.start(this@SplashActivity)
                finish()
            }
        }
    }
}