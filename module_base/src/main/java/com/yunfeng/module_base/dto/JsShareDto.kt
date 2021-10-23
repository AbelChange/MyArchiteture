package com.yunfeng.module_base.dto

import com.google.gson.annotations.SerializedName

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/6/25 16:49
 */
data class JsShareDto(@SerializedName("title") val title: String?,
                      @SerializedName("introduction") val introduction: String?,
                      @SerializedName("webUrl") val webUrl: String?,
                      @SerializedName("thumbImageUrl") val thumbImageUrl: String?)