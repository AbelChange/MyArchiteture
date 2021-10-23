package com.yunfeng.module_base.http

import android.os.Build
import android.text.TextUtils
import com.blankj.utilcode.util.DeviceUtils
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.yunfeng.lib.ext.base64
import com.yunfeng.lib.ext.encrypt
import com.yunfeng.lib.http.toJson
import com.yunfeng.lib.util.MMKVUtil
import com.yunfeng.module_base.config.ModuleConstant.HEAD_ENCRYPT_KEY
import com.yunfeng.module_base.service.RouterServiceManager
import java.util.*

/**
 * 获取手机验证码请求
 * Created by lianpengcheng on 2020/8/31.
 */
open class BaseHeader {

    @SerializedName("channel")
    @Expose
    private var channel: String? = null

    @SerializedName("systemOs")
    @Expose
    private var systemOs: String? = null

    @SerializedName("clientVer")
    @Expose
    private var clientVer: String? = null

    @SerializedName("clientId")
    @Expose
    private var clientId: String? = null

    @Expose
    @SerializedName("androidId")
    private var androidId: String? = null

    @Expose
    @SerializedName("macAddress")
    private var macAddress: String? = null

    @SerializedName("loginPlatform")
    @Expose
    private var loginPlatform: String? = null

    @SerializedName("loginToken")
    @Expose
    private var loginToken: String? = null

    @SerializedName("timeStamp")
    @Expose
    private var timeStamp: Long? = 0

    @Expose
    @SerializedName("sign")
    private var sign: String? = null

    /**
     * phoneModel  手机品牌型号
     */
    @Expose
    @SerializedName("phoneModel")
    private var phoneModel: String? = null

    /**
     * platformVer  操作系统型号，如：android 10 的 10
     */
    @Expose
    @SerializedName("platformVer")
    private var platformVer: String? = null

    @SerializedName("statusBarHeight")
    @Expose
    private var statusBarHeight: Int? = 30

    @SerializedName("cityId")
    @Expose
    private var cityId: String? = null

    init {
        channel = RouterServiceManager.getAppInfoService()?.channel()
        systemOs = getPhoneBrand()
        clientVer = RouterServiceManager.getAppInfoService()?.appVersion()
        clientId = getDeviceId()
        androidId = getAndroidID()
        macAddress = getMacAddress()
        loginPlatform = "android"
        platformVer = getPlatformType()
        phoneModel = getPhoneModel()
        loginToken = RouterServiceManager.getAccountService()?.getLoginToken()
        timeStamp = RouterServiceManager.getAppInfoService()?.serverTime()
        statusBarHeight = RouterServiceManager.getAppInfoService()?.getStatusBar()
        cityId = RouterServiceManager.getAppInfoService()?.getCityId()
        setSign()
    }

    private fun setSign() {
        var strContent = getSortParam()
        strContent = strContent.lowercase() // 需转小写
        val signBase64: String = base64(strContent)
        val base64WithKey = "$signBase64$HEAD_ENCRYPT_KEY"
        sign = encrypt(base64WithKey).lowercase() // 需转小写
    }

    private fun getSortParam(): String {
        val sortBefore: MutableMap<String, String?> = mutableMapOf()
        sortBefore["channel"] = channel
        sortBefore["clientId"] = clientId
        sortBefore["androidId"] = androidId
        sortBefore["macAddress"] = macAddress
        sortBefore["clientVer"] = clientVer
        sortBefore["loginPlatform"] = loginPlatform
        sortBefore["phoneModel"] = phoneModel
        sortBefore["platformVer"] = platformVer
        sortBefore["systemOs"] = systemOs
        sortBefore["timeStamp"] = timeStamp.toString()
        sortBefore["statusBarHeight"] = statusBarHeight.toString()
        sortBefore["loginToken"] = loginToken
        sortBefore["cityId"] = cityId
        var list: List<Map.Entry<String, String?>> =
            ArrayList<Map.Entry<String, String?>>(sortBefore.entries)
        list = list.sortedBy { it.key }
        val sb = StringBuilder()
        for ((_, value) in list) {
            if (!TextUtils.isEmpty(value)) {
                sb.append(value)
            }
        }
        return sb.toString()
    }

    private fun getDeviceId(): String? {
        return if (MMKVUtil.getBoolean(MMKVUtil.CHECK_USER_AGREEMENT_MAIN_PROTOCOL_PRIVACY)) DeviceUtils.getUniqueDeviceId() else null
    }

    private fun getAndroidID(): String? {
        return if (MMKVUtil.getBoolean(MMKVUtil.CHECK_USER_AGREEMENT_MAIN_PROTOCOL_PRIVACY)) DeviceUtils.getAndroidID() else null
    }

    private fun getMacAddress(): String? {
        return if (MMKVUtil.getBoolean(MMKVUtil.CHECK_USER_AGREEMENT_MAIN_PROTOCOL_PRIVACY)) DeviceUtils.getMacAddress() else null
    }

    private fun getPhoneBrand(): String? {
        return if (MMKVUtil.getBoolean(MMKVUtil.CHECK_USER_AGREEMENT_MAIN_PROTOCOL_PRIVACY)) Build.BRAND else null
    }

    private fun getPhoneModel(): String? {
        return if (MMKVUtil.getBoolean(MMKVUtil.CHECK_USER_AGREEMENT_MAIN_PROTOCOL_PRIVACY)) Build.MODEL else null
    }

    private fun getPlatformType(): String? {
        return if (MMKVUtil.getBoolean(MMKVUtil.CHECK_USER_AGREEMENT_MAIN_PROTOCOL_PRIVACY)) Build.VERSION.RELEASE else null
    }

    companion object {
        fun getHeader(): String {
            return BaseHeader().toJson()
        }
    }
}