package com.ablec.myarchitecture.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/2/2 16:32
 */
data class Person(
    @SerializedName("gender")
    @Expose
    var gender: String,
    @SerializedName("age")
    @Expose
    var age: Int,
    @SerializedName("tall")
    @Expose
    var tall: Boolean
)