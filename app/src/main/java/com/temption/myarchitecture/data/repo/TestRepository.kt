package com.temption.myarchitecture.data.repo

import com.temption.myarchitecture.data.server.api.TestApi
import com.temption.myarchitecture.data.server.dto.GetListReq
import com.yunfeng.lib.http.convert
import com.yunfeng.module_base.http.RetrofitServiceManager
import com.yunfeng.module_base.http.handleHttpResp

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/29 19:07
 */
class TestRepository {

    suspend fun getListData(req: GetListReq) =
        handleHttpResp { testApi.getListData(req.convert()) }

    companion object {
        private val testApi: TestApi
            get() = RetrofitServiceManager.getApiService(TestApi::class.java);
    }
}