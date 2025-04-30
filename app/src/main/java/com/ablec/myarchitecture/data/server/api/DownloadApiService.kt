package com.ablec.myarchitecture.data.server.api

import com.ablec.myarchitecture.data.server.dto.BaseResp
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface DownloadApiService {
    @GET
    @Streaming
    suspend fun downloadFile(@Url url: String): ResponseBody
}