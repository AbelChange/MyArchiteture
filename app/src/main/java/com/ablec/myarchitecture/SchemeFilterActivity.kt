package com.ablec.myarchitecture

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sankuai.waimai.router.common.DefaultUriRequest
import com.sankuai.waimai.router.core.OnCompleteListener
import com.sankuai.waimai.router.core.UriRequest
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

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
        DefaultUriRequest.startFromProxyActivity(this, object : OnCompleteListener {
            override fun onSuccess(request: UriRequest) {
                finish()
            }

            override fun onError(request: UriRequest, resultCode: Int) {
                finish()
            }
        })
    }
}