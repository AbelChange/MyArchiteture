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
 * @author HaoShuaiHui
 * @description:对okhttp框架层次设置证书信任
 * @date :2023/3/9 16:55
 */
/**
 * @receiver OkHttpClient.Builder
 * @param builder Builder
 * @param path String assets下证书路径
 * @param context Context
 * @return OkHttpClient.Builder
 */
fun OkHttpClient.Builder.certConfig(
    builder: OkHttpClient.Builder,
    path: String = "my_ca.crt",
    context: Context
): OkHttpClient.Builder {
    val trustManager = CustomX509TrustManager(context, path)
    builder.sslSocketFactory(trustManager.getSSLContext().socketFactory, trustManager)
    return builder
}

class CustomX509TrustManager(private val context: Context, private val certPath: String) :
    X509TrustManager {

    private val trustManagers = ArrayList<X509TrustManager>()

    init {
        val keyStore = getKeyStore()
        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
        tmf.init(keyStore)
        trustManagers.add(tmf.trustManagers[0] as X509TrustManager)
    }

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        trustManagers.forEach {
            it.checkClientTrusted(chain, authType)
        }
    }

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        trustManagers.forEach {
            it.checkServerTrusted(chain, authType)
        }
    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        val certificates = arrayListOf<X509Certificate>()
        trustManagers.forEach { manager ->
            manager.acceptedIssuers.forEach {
                certificates.add(it)
            }
        }
        return certificates.toTypedArray()
    }

    private fun getKeyStore(): KeyStore {
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null, null)
        var inputStream: InputStream? = null
        try {
            val cf =
                CertificateFactory.getInstance("X.509")
            // server 证书
            inputStream = context.assets.open(certPath)
            keyStore.setCertificateEntry("serverCert", cf.generateCertificate(inputStream))

            //系统 + 用户证 书
            val ks = KeyStore.getInstance("AndroidCAStore")
            ks.load(null)
            val aliases = ks.aliases()
            while (aliases.hasMoreElements()) {
                val alias = aliases.nextElement()
                if (alias.contains("user") && !BuildConfig.DEBUG) {
                    //只在调试模式下信任用户证书
                    continue
                }
                keyStore.setCertificateEntry(alias, ks.getCertificate(alias))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }
        return keyStore
    }

    fun getSSLContext(): SSLContext {
        val sslContext = SSLContext.getInstance("TLS")
        val array = arrayOfNulls<X509TrustManager>(trustManagers.size)
        trustManagers.toArray(array)
        sslContext.init(null, array, null)
        return sslContext
    }
}
