package com.ablec.myarchitecture.data.repo

import com.ablec.module_base.http.handleApiCall
import com.ablec.module_base.provider.BaseInitializer.Companion.GlobalContext
import com.ablec.module_base.util.convert
import com.ablec.myarchitecture.data.server.api.TestApiService
import com.ablec.myarchitecture.data.server.dto.GetListReq
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/29 19:07
 */
object TestRepository {

    private val apiService: TestApiService

    /**
     * 自定义入口
     */
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface TaskMonitorViewModelEntryPoint {
        fun getApiService(): TestApiService
    }

    init {
        apiService = EntryPointAccessors.fromApplication(
            GlobalContext,
            TaskMonitorViewModelEntryPoint::class.java
        ).getApiService()
    }

    suspend fun getListData(req: GetListReq) =
        handleApiCall { apiService.getListData(req.convert()) }


}