package com.ablec.myarchitecture.logic.main

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.ablec.lib.base.BaseActivity
import kotlinx.coroutines.delay

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2022/8/16 14:45
 */
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        hideSystemBar()
        lifecycleScope.launchWhenStarted {
            delay(1000)
            MainActivity.start(this@SplashActivity)
            finish()
        }
    }
}