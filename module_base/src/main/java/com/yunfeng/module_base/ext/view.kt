package com.yunfeng.module_base.ext

import com.blankj.utilcode.util.PermissionUtils
import com.yunfeng.lib.base.BaseActivity
import com.yunfeng.lib.ext.startLocationService
import com.yunfeng.module_base.R

/**
 * Desc: ***
 * copyright，shfengqu.com
 * Author: XiaoLongCai
 * Date: 2021/6/18 18:29
 * Email: caixiaolong@yungengxin.com
 */

fun BaseActivity.showLocationServiceDialog() {
    showCommonDialog(
        content = getString(R.string.module_base_location_service_tip),
        cancelStr = "",
        okClickListener = {
            startLocationService()
        })
}

fun BaseActivity.showLocationPermissionDialog() {
    showCommonDialog(
        content = getString(R.string.module_base_location_permission_tip),
        okClickListener = {
            PermissionUtils.launchAppDetailsSettings()
        })
}