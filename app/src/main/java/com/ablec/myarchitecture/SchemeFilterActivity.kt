package com.ablec.myarchitecture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter


/**
 * @Description:    外部跳转统一交给router处理
 * @Author:         haoshuaihui
 * @CreateDate:     2021/4/30 13:11
 */
class SchemeFilterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //1.可以这里对跳转进行统一处理

        //2.也可以直接交给router处理（业务可能需要拦截器）
        val uri = intent.data
        ARouter.getInstance().build(uri).navigation()
        finish()
    }
}