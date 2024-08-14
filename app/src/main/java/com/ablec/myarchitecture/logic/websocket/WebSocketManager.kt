package com.ablec.myarchitecture.logic.websocket

import com.ablec.module_base.AppExecutors
import com.blankj.utilcode.util.LogUtils
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit
import javax.net.SocketFactory

/**
 * @Description:    房间内socket通信
 * @Author:         haoshuaihui
 * @CreateDate:     2021/2/3 13:29
 */
object WebSocketManager {

    private val tag = javaClass.simpleName

    private var mSocket: WebSocket? = null
    private var mListener: OnSocketListener? = null
    private val retryStrategy = FibonacciRetryStrategyImpl()
    private val httpClient = OkHttpClient
        .Builder()
        .socketFactory(SocketFactory.getDefault())
        .readTimeout(3, TimeUnit.SECONDS)//设置读取超时时间
        .writeTimeout(3, TimeUnit.SECONDS)//设置写的超时时间
        .connectTimeout(3, TimeUnit.SECONDS)//设置连接超时时间
        .pingInterval(10, TimeUnit.SECONDS) // 设置 PING 帧发送间隔
        .retryOnConnectionFailure(false)
        .build()

    fun connect(webSocketUrl: String) {
        if (mSocket != null) {
            return
        }
        val request = Request.Builder()
            .url(webSocketUrl)
            .build()
        httpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                AppExecutors.getInstance().mainThread().execute {
                    mSocket = null
                    mListener?.onClose(code, reason)
                }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                AppExecutors.getInstance().mainThread().execute {
                    mSocket = null
                    mListener?.onClose(code, reason)
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                AppExecutors.getInstance().mainThread().execute {
                    mSocket = null
                    LogUtils.e(tag, "onFailure" + response.toString())
                    mListener?.onFail(t)
                    retryStrategy.retry{
                        LogUtils.w("WebSocketManager", "----------------------重连----------------------")
                        connect(webSocketUrl)
                    }
                }
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                AppExecutors.getInstance().mainThread().execute {
                    LogUtils.w(tag, "onMessage${text}")
                    mListener?.onMsg(text)
                }
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                AppExecutors.getInstance().mainThread().execute {
                    LogUtils.d(tag, "onMessageBytes" + bytes.toString())
                }
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                AppExecutors.getInstance().mainThread().execute {
                    LogUtils.d(tag, "onOpen" + response.code)
                    mSocket = webSocket
                    retryStrategy.reset()
                }
            }
        })
    }

    fun setListener(listener: OnSocketListener) {
        mListener = listener
    }

    fun release() {
        try {
            mSocket?.close(4000, "退出直播间")
            retryStrategy.reset()
        } catch (e: Exception) {
            LogUtils.e(tag, e.message)
        } finally {
            mSocket = null
            mListener = null
        }
    }

    interface OnSocketListener {
        fun onClose(code: Int, reason: String)
        fun onFail(t: Throwable)
        fun onMsg(text: String)
    }


}


