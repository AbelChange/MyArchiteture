package com.yunfeng.module_base.server.api

import com.yunfeng.lib.http.base.BaseResp
import com.yunfeng.module_base.dto.DayTimeListDto
import com.yunfeng.module_base.dto.TimeListDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * 基础信息接口
 */
interface BaseApi {

    /**
     *读取预定时间列表
     */
    @GET("order/timeList")
    suspend fun timeList(@Query("drama_id") cityCode: String?): BaseResp<TimeListDto>

    /**
     *读取指定日期可用预定时间
     */
    @GET("order/dayTimeList")
    suspend fun daytimeList(@QueryMap serializeToMap: Map<String, String>): BaseResp<DayTimeListDto>

    /**
     *读取指定日期可用预定时间
     */
    @GET("teamOrder/dayTimeList")
    suspend fun teamOrderDayTimeList(@QueryMap serializeToMap: Map<String, String>): BaseResp<DayTimeListDto>
}