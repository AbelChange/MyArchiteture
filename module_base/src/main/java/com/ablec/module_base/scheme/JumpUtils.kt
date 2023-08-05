package com.ablec.module_base.scheme

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

object JumpUtils {

    /**
     * scheme
     */
    private const val SCHEME = "dm://"

    /**
     * 打开网页
     */
    private const val OPEN_WEB = SCHEME + "open.dm/openWebPage"

    /**
     * 打开浏览器
     */
    private val OPEN_BROWSER: String = SCHEME + "open.dm/browser"

    /**
     * 打开系统消息
     */
    private val OPEN_MESSAGE: String = SCHEME + "open.dm/openServerMessage"

    /**
     * 打开剧本详情
     */
    private val OPEN_SCRIPT_DETAIL: String = SCHEME + "open.dm/openScriptDetail"

    /**
     * 打开团购详情
     */
    private val OPEN_GROUP_DETAIL: String = SCHEME + "open.dm/openScriptGroupDetail"

    /**
     * 已经下单，跳转native确认支付页面
     */
    private val OPEN_PAY_ORDER: String = SCHEME + "open.dm/openOrderInfoActivity"

    /**
     * 打开订单详情
     */
    private val OPEN_ORDER_DETAIL: String = SCHEME + "open.dm/openOrderDetail"

    /**
     * 打开优惠券列表
     */
    private val OPEN_COUPON: String = SCHEME + "open.dm/coupon"

    /**
     * 邀请好友
     */
    private val OPEN_LOGIN: String = SCHEME + "open.dm/openLoginPage"

    /**
     * 打开新人福利
     */
    private val INVITE_FRIEND: String = SCHEME + "open.dm/inviteFriend"

    /**
     * 打开新人福利
     */
    private val OPEN_NEW_USER: String = SCHEME + "open.dm/newUserWelfare"

    /**
     * 打开DM个人主页
     */
    private val OPEN_DM_INFO: String = SCHEME + "open.dm/dmHomePage"

    /**
     * 是否是app协议
     * 是的话统一交给schemeFilter中转处理
     * @param url
     * @return
     */
    fun dealProtocol(context: Context, url: String?): Boolean {
        if (url?.startsWith(SCHEME) == true) {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            return true
        }
        return false
    }

    /**
     * 协议跳转
     *
     * @param context activity context
     * @param url
     */
    fun jump(context: Context?, url: String?) {
        if (context == null || url.isNullOrEmpty()) {
            return
        }
        when {

        }
    }

    private fun getUrlParams(url: String, key: String): String {
        try {
            val tempMask = "$key="
            val decodeUrl = URLDecoder.decode(url, "utf-8")
            return decodeUrl.substring(decodeUrl.lastIndexOf(tempMask) + tempMask.length)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return ""
    }

    private fun jumpToBrowser(context: Context, url: String) {
        if (TextUtils.isEmpty(url)) {
            return
        }
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}