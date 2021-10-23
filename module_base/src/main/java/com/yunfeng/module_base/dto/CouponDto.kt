package com.yunfeng.module_base.dto

import androidx.annotation.Keep

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/6/8 15:13
 */

@Keep
data class CouponDto(
    @SerializedName("coupon_id")
    val couponId: String?,
    @SerializedName("coupon_status")
    val couponStatus: Int?,//1 待使用，2 已使用，3 已过期
    @SerializedName("expired_time")
    val expiredTime: Long = 0,//过期时间戳，单位：秒
    @SerializedName("id")//优惠券id
    val id: String?,
    @SerializedName("intro")
    val intro: String?,
    @SerializedName("met_amount")
    val metAmount: String?,//满金额
    @SerializedName("reduce_amount")
    val reduceAmount: String?,//减金额
    @SerializedName("title")
    val title: String?,
    @SerializedName("limit_type")
    val limitType: String? = LIMIT_TYPE_NORMAL
) : Serializable {
    companion object {
        const val LIMIT_TYPE_NORMAL = "1" //普通
        const val LIMIT_TYPE_GROUP = "2" //拼团
    }
}



