package com.temption.myarchitecture.data.server.dto

import com.google.gson.annotations.SerializedName

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/4/28 15:39
 */
data class GetListReq(@SerializedName("pno") val pageNo: Int,
                      @SerializedName("ps") val pageSize: Int)

data class ListItem(
    val firstImg: String,
    val id: String,
    val mark: String,
    val source: String,
    val title: String,
    val url: String
)

