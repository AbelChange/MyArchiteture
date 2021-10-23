package com.yunfeng.module_base.http

import com.yunfeng.module_base.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/29 19:26
 */
object RetrofitServiceManager {

    private val mDefaultRetrofitBuilder: Retrofit.Builder by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClientManager.instance)
    }



    /**
     * @param baseUrl API base URL
     * @param service Service 的 class
     * @return
    </T> */
    fun <T> getApiService(
        service: Class<T>,
        baseUrl: String = BuildConfig.BASE_URL,
    ): T {
        return mDefaultRetrofitBuilder
            .baseUrl(baseUrl)
            .build()
            .create(service)
    }


}