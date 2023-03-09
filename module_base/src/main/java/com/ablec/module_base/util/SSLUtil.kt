package com.ablec.module_base.util

import android.content.Context
import okhttp3.OkHttpClient
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import kotlin.collections.ArrayList

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2023/3/9 16:55
 */
/**
 * 设置证书
 * @receiver OkHttpClient.Builder
 * @param builder Builder
 * @param path String
 * @param context Context
 * @return OkHttpClient.Builder
 */
fun OkHttpClient.Builder.sslSocketFactory(builder: OkHttpClient.Builder, path: String,context: Context): OkHttpClient.Builder {
    val sslUtil = SSLUtil()
    sslUtil.addCertificate(path, context)
    builder.sslSocketFactory(sslUtil.getSSLSocketFactory(), sslUtil.mX509TrustManager)
    return builder
}

class SSLUtil {

    val mX509TrustManager = getTrustManager()

    // 证书数据
    private var CERTIFICATES_DATA = ArrayList<ByteArray?>()


    /**
     * 添加https证书
     *
     * @param inputStream
     */
    @Synchronized
    fun addCertificate(path: String, context: Context) {

        val inputStream = context.assets.open(path)

        try {
            var len = 0// 数据总长度
            val data = ArrayList<ByteArray>()
            var ava = inputStream.available() // 数据当次可读长度
            while (ava > 0) {
                val buffer = ByteArray(ava)
                inputStream.read(buffer)
                data.add(buffer)
                len += ava
                ava = inputStream.available()
            }

            val buff = ByteArray(len)
            var dstPos = 0
            for (bytes in data) {
                val length = bytes.size
                System.arraycopy(bytes, 0, buff, dstPos, length)
                dstPos += length
            }

            CERTIFICATES_DATA.add(buff)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    /**
     * 获取sslSocketFactory() 中需要的参数  X509TrustManager
     */
    private fun getTrustManager(): X509TrustManager {

        val trustManagerFactory = TrustManagerFactory.getInstance(
            TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(getKeyStore())
        val trustManagers = trustManagerFactory.trustManagers;
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw  IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers))
        }
        return trustManagers[0] as X509TrustManager

    }


    /**
     * 获取sslSocketFactory() 中需要的参数  SSLSocketFactory
     */
    fun getSSLSocketFactory(): SSLSocketFactory {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf(mX509TrustManager), null)
        return sslContext.socketFactory
    }


    private fun getKeyStore(): KeyStore {

        // 添加证书
        val certificates = ArrayList<InputStream>()

        // 将字节数组转为输入流数组
        if (CERTIFICATES_DATA.isNotEmpty()) {
            for (bytes in CERTIFICATES_DATA) {
                certificates.add(ByteArrayInputStream(bytes))
            }
        }

        val certificateFactory = CertificateFactory.getInstance("X.509")
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null)
        try {
            var i = 0
            val size = certificates.size
            while (i < size) {
                val certificate = certificates[i]
                val certificateAlias = Integer.toString(i++)
                keyStore.setCertificateEntry(certificateAlias, certificateFactory
                    .generateCertificate(certificate))
                certificate?.close()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return keyStore
    }

}