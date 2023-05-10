package com.ablec.myarchitecture.data.server.api

import com.ablec.myarchitecture.data.server.dto.BaseResp
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.QueryMap

/**
 * 上传头像接口
 */
interface UploadApiService {

    /**
     * 上传图片
     *
     * @return
     */
    @Multipart
    @POST("uploadImage")
    suspend fun uploadPic(@Part part: MultipartBody.Part,
                          @QueryMap map: Map<String, String>): BaseResp<Unit>
}