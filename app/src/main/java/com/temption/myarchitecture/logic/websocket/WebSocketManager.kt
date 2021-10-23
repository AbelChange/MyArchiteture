package com.temption.myarchitecture.logic.websocket

import com.blankj.utilcode.util.LogUtils
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/2/3 13:29
 */
object WebSocketManager {

    fun connect(hostName: String, port: Int): WebSocket {
        val httpClient = OkHttpClient.Builder()
            .pingInterval(10, TimeUnit.SECONDS) // 设置 PING 帧发送间隔
            .build()
        val webSocketUrl = "ws://${hostName}:${port}"
        val request = Request.Builder()
            .url(webSocketUrl)
            .build()
        return  httpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                LogUtils.d("onClosed")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                LogUtils.d("onClosing")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                LogUtils.d("onFailure")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                LogUtils.d("onMessage")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                LogUtils.d("onMessageBytes"+bytes.toString())
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                LogUtils.d("onOpen"+response.code)
                webSocket.send("哟西")
            }
        })
    }

}