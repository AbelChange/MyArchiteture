package com.ablec.myarchitecture.data.repo

import com.ablec.myarchitecture.data.server.api.TestApi
import com.ablec.myarchitecture.data.server.dto.GetListReq
import com.ablec.module_base.http.convert
import com.ablec.module_base.http.handleHttpResp

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