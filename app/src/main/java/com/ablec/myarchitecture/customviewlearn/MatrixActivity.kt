package com.ablec.myarchitecture.customviewlearn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ablec.lib.base.BaseActivity
import com.ablec.lib.ext.viewBinding
import com.ablec.myarchitecture.databinding.ActivityMatrixBinding

/**
 * @author shuaihui_hao
 * @date 2024/6/14
 * @description
 */
class MatrixActivity : BaseActivity() {

    private val binding: ActivityMatrixBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MatrixActivity::class.java)
            context.startActivity(starter)
        }
    }


}