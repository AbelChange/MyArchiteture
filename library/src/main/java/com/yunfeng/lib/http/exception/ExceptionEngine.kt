package com.yunfeng.lib.http.exception

import android.net.ParseException
import android.util.MalformedJsonException
import com.blankj.utilcode.util.LogUtils
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ExceptionEngine {
    const val TAG = "ExceptionEngine"

    //客户端报错
    const val UN_KNOWN_ERROR = 9000 //未知错误
    const val ANALYTIC_SERVER_DATA_ERROR = 9001 //解析(服务器)数据错误
    const val ANALYTIC_CLIENT_DATA_ERROR = 9002 //解析(客户端)数据错误
    const val CONNECT_ERROR = 9003 //网络连接错误
    const val TIME_OUT_ERROR = 9004 //网络连接超时
    const val UNKNOWNHOSTEXCEPTION = 9005 //网络连接超时
    const val LOGIN_EXPIRED = 401
    fun handleException(e: Throwable): ApiException {
        e.printStackTrace()
        val ex: ApiException
        return if (e is HttpException) {
            ex = ApiException(e, e.code())
            if (e.code() == LOGIN_EXPIRED) {
                ex.message = "登录已过期"
            } else {
                ex.message = "服务器错误，请稍后再试"
            }
            ex
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException || e is MalformedJsonException
        ) {  //解析数据错误
            LogUtils.e(TAG, e)
            ex = ApiException(e, ANALYTIC_SERVER_DATA_ERROR)
            ex.message = "解析失败，请稍后再试"
            ex
        } else if (e is ConnectException) {
            ex = ApiException(e, CONNECT_ERROR)
            ex.message = "网络连接错误，请稍后再试"
            ex
        } else if (e is SocketTimeoutException) {
            ex = ApiException(e, TIME_OUT_ERROR)
            ex.message = "网络连接超时，请稍后再试"
            ex
        } else if (e is UnknownHostException) {
            ex = ApiException(e, UNKNOWNHOSTEXCEPTION)
            ex.message = "网络异常，请检查您的网络连接"
            ex
        } else {  //未知错误
            ex = ApiException(e, UN_KNOWN_ERROR)
            ex.message = "未知错误," + e.message
            ex
        }
    }
}