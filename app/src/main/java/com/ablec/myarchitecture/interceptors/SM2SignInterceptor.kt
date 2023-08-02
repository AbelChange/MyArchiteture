package com.droi.hotshopping.data.source.remote


import com.google.gson.JsonParser
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset

class SM2SignInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = addSign(chain.request())
        return chain.proceed(request)
    }

    private fun addSign(request: Request): Request {
        var ciphertext: String? = null
        //看文档指定
        val method = request.method
        if (method.equals("GET", true) || method.equals("DELETE", true)) {
            ciphertext = request.url.queryParameter("data") ?: ""
        } else {
            val requestBody = request.body as RequestBody
            val contentType = requestBody.contentType()
            if (contentType != null) {
                /*如果是二进制上传  则不进行加密*/
                if (contentType.type.equals("multipart", false)) {
                    return request
                }
            }
            /*获取请求的数据*/
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            val charset = requestBody.contentType()!!.charset(Charset.forName("UTF-8"))!!
            val bodyContent = buffer.readString(charset).trim()
            ciphertext = JsonParser().parse(bodyContent).asJsonObject.get("data").asString
        }
        //待签名的文本
        val originString = ciphertext
        if (originString.isNullOrBlank()){
            return request
        }
//        val sm2 = SM2Util()
//        val signedByte = sm2.sign(originString, PRI_KEY)
//        //签名后HexString
//        val signedHexString = EncodeUtil.byteToHex(signedByte)

        return request.newBuilder()
            .addHeader("sign", "signedHexString")
            .build()
    }

    companion object {
        private const val TAG = "SM2Sign"

        //用来对参数加签
        const val PRI_KEY = "1f48c3e96c66a718ea1a61f05884069df7ebd141d9d3f80a525a193b34ed30e4"

        //用来对服务端返回数据签名校验 暂时不校验（非银行类 不考虑前端数据错误影响）
        const val PUB_KEY_SERVER =
            "043d9d4cc71a285af936b36880fd4d6155c22957cd2c84ea313469065207fb951b9ef1db79d69af8886e91e833da1ebc6bfdde86e70f52923d6e042eaa147624c7"
    }

}