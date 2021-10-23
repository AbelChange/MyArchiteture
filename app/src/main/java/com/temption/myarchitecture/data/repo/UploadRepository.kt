package com.temption.myarchitecture.data.repo

import com.temption.myarchitecture.BuildConfig
import com.temption.myarchitecture.data.server.api.UploadApi
import com.yunfeng.lib.http.base.BaseResp
import com.yunfeng.module_base.http.RetrofitServiceManager
import com.yunfeng.module_base.http.handleHttpResp
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadRepository {

    /**
     * 仅供身份证上传使用，上传头像等请重新定义方法
     * @param dir 上传目录
    订单评价：comment
    头像：avatar
    店铺图片：store_image
     */
    suspend fun uploadIcCardPic(dir: String, path: String): BaseResp<Void> {
        return handleHttpResp {
            uploadApi.uploadPic(
                generatorPart(path),
                mutableMapOf("type" to dir)
            )
        }
    }

    /**
     *  @param dir 上传目录
    订单评价：comment
    头像：avatar
    店铺图片：store_image
    授权意向图片:delegate
     */
    suspend fun uploadPic(dir: String, path: String): BaseResp<Void> {
        return handleHttpResp {
            uploadApi.uploadPic(
                generatorPart(path),
                mutableMapOf("type" to dir)
            )
        }
    }

    private fun generatorPart(path: String): MultipartBody.Part {
        val file = File(path)
        val photo: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", file.name, photo)
    }

    companion object {
        private val uploadApi: UploadApi
            get() = RetrofitServiceManager.getApiService(
                UploadApi::class.java,
                BuildConfig.BASE_URL
            )
    }
}