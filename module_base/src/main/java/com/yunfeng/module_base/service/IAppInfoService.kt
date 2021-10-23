package com.yunfeng.module_base.service

import android.content.Context

interface IAppInfoService {

    fun channel(): String?
    fun appVersion(): String
    fun serverTime(): Long
    fun getStatusBar(): Int
    fun getCityId(): String
    fun startDramaDetail(context: Context, id: String?)
    fun startMainActivity(context: Context)
    fun startServerMessageActivity(context: Context)
    fun startCouponListActivity(context: Context)
    fun cancelWebGroupOrder(order: String?)
    fun startCouponTicketsAvailableActivity(context: Context, teamOrderNo: String, groupNo: String)
    fun startAliAuth(context: Context)
    fun startDmPage(context: Context, id: String)
}