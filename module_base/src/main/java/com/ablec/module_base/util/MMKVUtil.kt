package com.ablec.module_base.util

import android.content.Context
import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 * 保存简单数据
 */
object MMKVUtil {
    /**
     * 应用更新下载id
     */
    const val KEY_UPDATE_DOWNLOAD_ID = "key_update_download_id"

    /**
     * 登录用户账号信息
     */
    const val KEY_ACCOUNT_INFO = "account_info"

    /**
     * 用户协议是否否选
     */
    const val KEY_CHECK_USER_AGREEMENT = "check_user_agreement"

    /**
     * 首页协议与隐私是否否选
     */
    const val CHECK_USER_AGREEMENT_MAIN_PROTOCOL_PRIVACY =
        "check_user_agreement_main_protocol_privacy_1.1"

    /**
     * DM服务引导
     */
    const val KEY_DM_SERVICE_GUIDE = "dm_service_guide"

    @JvmStatic
    private var kv: MMKV? = null

    fun init(context: Context) {
        MMKV.initialize(context)
        kv = MMKV.defaultMMKV()
    }

    fun putString(key: String, value: String?) {
        if (value == null) {
            kv?.remove(key)
        } else {
            kv?.encode(key, value)
        }
    }

    fun getString(key: String, defaultValue: String? = null): String? {
        return kv?.decodeString(key, defaultValue) ?: defaultValue
    }

    fun putInt(key: String, value: Int) {
        kv?.encode(key, value)
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return kv?.decodeInt(key, defaultValue) ?: defaultValue
    }

    fun putLong(key: String, value: Long) {
        kv?.encode(key, value)
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return kv?.decodeLong(key, defaultValue) ?: defaultValue
    }

    fun putFloat(key: String, value: Float) {
        kv?.encode(key, value)
    }

    fun getFloat(key: String, defaultValue: Float = 0f): Float {
        return kv?.decodeFloat(key, defaultValue) ?: defaultValue
    }

    fun putDouble(key: String, value: Double) {
        kv?.encode(key, value)
    }

    fun getDouble(key: String, defaultValue: Double = 0.0): Double {
        return kv?.decodeDouble(key, defaultValue) ?: defaultValue
    }

    fun putBoolean(key: String, value: Boolean) {
        kv?.encode(key, value)
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return kv?.decodeBool(key, defaultValue) ?: defaultValue
    }

    fun putParcelable(key: String, value: Parcelable?) {
        if (value == null) {
            kv?.remove(key)
        } else {
            kv?.encode(key, value)
        }
    }

    fun <T : Parcelable?> getParcelable(key: String?, tClass: Class<T>?): T? {
        return kv?.decodeParcelable(key, tClass)
    }

    fun remove(key: String) {
        kv?.remove(key)
    }
}