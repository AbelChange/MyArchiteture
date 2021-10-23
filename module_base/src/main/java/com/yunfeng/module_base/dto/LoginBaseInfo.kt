package com.yunfeng.module_base.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 *
 * Created by yangrong on 2021/5/25.
 */
@Parcelize
data class LoginBaseInfo(
    var accid: String?,
    var avatar: String?,
    var mobile: String?,
    var neteaseToken: String?,
    var nickname: String?,
    var sync_userinfo: Int,
    @SerializedName("user_id") var userId: String?,
    @SerializedName("is_dm") var isDm: String?,
    @SerializedName("certify_tag") var isCertify: String?,
    @SerializedName("adult_tag") var isAdult: String?
) : Parcelable