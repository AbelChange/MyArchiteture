package com.ablec.myarchitecture.data.server.api

import com.ablec.myarchitecture.data.server.dto.ListItem
import com.ablec.module_base.http.base.BaseResp
import com.ablec.module_base.http.base.PageData
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * 账号信息请求接口
 * Created by lianpengcheng on 2020/8/31.
 */
interface TestApiService {

    @GET("http://v.juhe.cn/weixin/query?key=27c0dc2798c1cfe2d583385a4e92a1f8")
    suspend fun getListData(@QueryMap serializeToMap: Map<String, String>): BaseResp<PageData<ListItem>>


}