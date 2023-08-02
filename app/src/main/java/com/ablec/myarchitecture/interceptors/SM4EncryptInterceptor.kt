package com.droi.hotshopping.data.source.remote

import android.util.Base64
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import org.json.JSONObject
import java.io.IOException
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset

class SM4EncryptInterceptor : Interceptor {
//    private val sm4 = SM4Utils()
//
//    init {
//        //设置 密钥 16长度的字符
//        sm4.secretKey = """65h<F"""
//        //设置 向量 16长度的字符
//        sm4.iv = ""
//        //声明密钥和向量是否是32长度的十六进制的字符串，如果true则需要设置密钥向量都是十六进制的32长度字符串。
//        sm4.isHexString = false
//    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = encryptRequest(chain.request())
        val response = chain.proceed(newRequest)
        return decryptResponse(response)
    }


    private fun encryptRequest(request: Request): Request {
        val url = request.url
        val method = request.method
        /*本次请求的接口地址*/
        val apiPath = "${url.scheme}://${url.host}:${url.port}${url.encodedPath}".trim()
        /*如果请求方式是Get或者Delete，此时请求数据是拼接在请求地址后面的*/
        if (method.equals("GET", true) || method.equals("DELETE", true)) {
            /*如果有请求数据 则加密*/
            if (url.encodedQuery != null) {
                val queryParamsNames =
                    "${url.encodedQuery}&timestamp=${request.headers["timestamp"]}"
                //SM4加密
                val encryptQueryParamNames = "".toByteArray() //sm4.encryptData_ECB(queryParamsNames)
                //Base64编码
                val base64EncryptQueryParamNames =
                    Base64.encodeToString(encryptQueryParamNames, Base64.DEFAULT).trim()

                //拼接加密后的url，参数字段自己跟后台商量，这里我用param，后台拿到数据先对param进行解密，解密后的数据就是请求的数据
                val newUrl =
                    "$apiPath?data=${URLEncoder.encode(base64EncryptQueryParamNames, "UTF-8")}"
                //构建新的请求
                return request.newBuilder()
                    .removeHeader("X-Data-Raw")
                    .url(newUrl)
                    .build()
            }
        } else {
            //不是Get和Delete请求时，则请求数据在请求体中
            val requestBody = request.body

            var charset = Charset.forName("UTF-8")

            /*判断请求体是否为空  不为空则执行以下操作*/
            if (requestBody != null) {
                val contentType = requestBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(charset)
                    /*如果是二进制上传  则不进行加密*/
                    if (contentType.type.equals("multipart", false)) {
                        return request
                    }
                }

                /*获取请求的数据*/
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                val bodyString = buffer.readString(charset).trim()
                val wrapperBody = "${bodyString}&timestamp=${request.headers["timestamp"]}"
                val requestData = URLDecoder.decode(wrapperBody, "utf-8")
                //SM4加密
                val encryptData ="".toByteArray()// sm4.encryptData_ECB(requestData)
                //Base64编码
                val base64EncryptData =
                    Base64.encodeToString(encryptData, Base64.DEFAULT).trim()
                val jsonStr = JsonObject().apply {
                    addProperty("data", base64EncryptData)
                }.toString()
                /*构建新的请求体*/
                val newRequestBody = jsonStr.toRequestBody(MEDIA_TYPE)

                /*构建新的requestBuilder*/
                val newRequestBuilder = request.newBuilder()
                    .removeHeader("Content-Type")
                    .removeHeader("Content-Length")
                    .removeHeader("X-Data-Raw")

                //根据请求方式构建相应的请求
                when (method) {
                    "POST" -> newRequestBuilder.post(newRequestBody)
                    "PUT" -> newRequestBuilder.put(newRequestBody)
                }
                return newRequestBuilder.build()
            }
        }
        return request.newBuilder().removeHeader("X-Data-Raw").build()
    }

    private fun decryptResponse(response: Response): Response {
        if (response.isSuccessful) {
            val responseBody = response.body
            if (responseBody != null) {
                /*开始解密*/
                val source = responseBody.source()
                source.request(java.lang.Long.MAX_VALUE)
                val buffer = source.buffer
                var charset = Charset.forName("UTF-8")
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(charset)
                }
                //密文数据字符串
                val bodyString = buffer.clone().readString(charset)
                //Base64解码
                val bodyByte = Base64.decode(bodyString, Base64.DEFAULT)
                //SM4解密
                val responseData = ""//sm4.decryptData_ECB(bodyByte) ?: return response

                /*将解密后的明文返回*/
                val newResponseBody = responseData.trim().toResponseBody(contentType)
                return response.newBuilder().body(newResponseBody).build()
            }
        }
        return response
    }

    companion object {
        private const val TAG = "SM4Encrypt"
        private val MEDIA_TYPE = "application/json".toMediaType()
    }
}