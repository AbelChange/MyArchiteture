package com.ablec.module_base.http


import android.net.ParseException
import android.util.MalformedJsonException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2022/3/2 16:44
 */

/**
 * api自定义Exception
 */
class ApiException(cause: Throwable?, var code: Int) : Exception(cause) {

    /**
     * 错误信息
     */
    override var message: String? = null
}

/**
 * 收拢处理
 */
object ExceptionEngine {
    const val TAG = "ExceptionEngine"

    //客户端报错
    private const val UN_KNOWN_ERROR = 9000 //未知错误
    private const val ANALYTIC_SERVER_DATA_ERROR = 9001 //解析(服务器)数据错误
    const val ANALYTIC_CLIENT_DATA_ERROR = 9002 //解析(客户端)数据错误
    private const val CONNECT_ERROR = 9003 //网络连接错误
    private const val TIME_OUT_ERROR = 9004 //网络连接超时
    private const val UNKNOWNHOSTEXCEPTION = 9005 //网络连接超时
    private const val LOGIN_EXPIRED = 401
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
        ) {  //解析数据错误 log
            ex = ApiException(e, ANALYTIC_SERVER_DATA_ERROR)
            ex.message = "加载失败"
            ex
        } else if (e is ConnectException) {
            ex = ApiException(e, CONNECT_ERROR)
            ex.message = "网络错误"
            ex
        } else if (e is SocketTimeoutException) {
            ex = ApiException(e, TIME_OUT_ERROR)
            ex.message = "网络超时"
            ex
        } else if (e is UnknownHostException) {
            ex = ApiException(e, UNKNOWNHOSTEXCEPTION)
            ex.message = "网络错误"
            ex
        } else {  //未知错误
            ex = ApiException(e, UN_KNOWN_ERROR)
            ex.message = "未知错误,请稍后再试"
            ex
        }
    }
}