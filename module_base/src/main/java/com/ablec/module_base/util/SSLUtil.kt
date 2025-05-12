package com.ablec.module_base.util


import android.content.Context
import com.ablec.module_base.BuildConfig
import okhttp3.OkHttpClient
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/**
 * 对 OkHttp 框架层次设置证书信任防止抓包
 */
fun OkHttpClient.Builder.certConfig(
    path: String = "my_ca.crt",  // 默认证书路径
    context: Context
): OkHttpClient.Builder {
    val trustManager = CustomX509TrustManager(context, path)
    sslSocketFactory(trustManager.getSSLContext().socketFactory, trustManager)
    return this
}

/**
 * 自定义 X509TrustManager 实现，用于处理证书验证
 */
class CustomX509TrustManager(
    private val context: Context,
    private val certPath: String
) : X509TrustManager {

    private val trustManagers: List<X509TrustManager>

    init {
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(getKeyStore())
        trustManagers = tmf.trustManagers.filterIsInstance<X509TrustManager>()
    }

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        trustManagers.forEach { it.checkClientTrusted(chain, authType) }
    }

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        trustManagers.forEach { it.checkServerTrusted(chain, authType) }
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return trustManagers.flatMap { it.acceptedIssuers.toList() }.toTypedArray()
    }

    /**
     * 获取构建的 KeyStore
     */
    private fun getKeyStore(): KeyStore {
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply { load(null, null) }
        val cf = CertificateFactory.getInstance("X.509")
        var inputStream: InputStream? = null

        try {
            // 加载自定义证书
            inputStream = context.assets.open(certPath)
            val cert = cf.generateCertificate(inputStream) as X509Certificate
            keyStore.setCertificateEntry("serverCert", cert)

            // 加载系统证书
            val ks = KeyStore.getInstance("AndroidCAStore").apply { load(null) }
            val aliases = ks.aliases()
            while (aliases.hasMoreElements()) {
                val alias = aliases.nextElement()
                if (alias.contains("user") && !BuildConfig.DEBUG) {
                    // release 禁止用户证书，防止抓包
                    continue
                }
                keyStore.setCertificateEntry(alias, ks.getCertificate(alias))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close() // 自动关闭资源
        }

        return keyStore
    }

    /**
     * 获取 SSLContext 配置
     */
    fun getSSLContext(): SSLContext {
        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, trustManagers.toTypedArray(), null)
        }
        return sslContext
    }
}
