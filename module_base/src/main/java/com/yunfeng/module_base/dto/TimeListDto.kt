package com.yunfeng.module_base.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * 下单时间
 */
@Parcelize
data class TimeListDto(
    @SerializedName("days") val timeDayDto: MutableList<TimeDayDto>?,
    @SerializedName("times") val timeHourDto: MutableList<TimeHourDto>?
) : Parcelable

@Parcelize
data class DayTimeListDto(
    @SerializedName("day") val timeDayDto: TimeDayDto?,
    @SerializedName("times") val timeHourDto: MutableList<TimeHourDto>?
) : Parcelable

@Parcelize
data class TimeDayDto(
    @SerializedName("title") val title: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("day") val day: String?,
    @SerializedName("dm_count") val dmCount: String?,
    @SerializedName("full_booked") val fullBooked: String?,
) : Parcelable

@Parcelize
data class TimeHourDto(
    @SerializedName("hour") val hour: String?,
    @SerializedName("status") var status: String?,
    @SerializedName("dm_count") val dmCount: String?
) : Parcelable
