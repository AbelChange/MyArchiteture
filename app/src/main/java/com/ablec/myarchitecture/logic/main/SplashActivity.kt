package com.ablec.myarchitecture.logic.main

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.ablec.myarchitecture.databinding.SimpleTextviewBinding
import com.ablec.lib.base.BaseActivity

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2022/8/16 14:45
 */
class SplashActivity : BaseActivity() {
    private lateinit var binding: SimpleTextviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        hideSystemBar()
        binding = SimpleTextviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launchWhenStarted {
            MainActivity.start(this@SplashActivity)
            finish()
        }
    }
}