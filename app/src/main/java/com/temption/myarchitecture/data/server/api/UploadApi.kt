package com.temption.myarchitecture.data.server.api

import com.yunfeng.lib.http.base.BaseResp
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.QueryMap

/**
 * 上传头像接口
 */
interface UploadApi {

    /**
     * 上传图片
     *
     * @return
     */
    @Multipart
    @POST("uploadImage")
    suspend fun uploadPic(@Part part: MultipartBody.Part,
                          @QueryMap map: Map<String, String>): BaseResp<Void>
}