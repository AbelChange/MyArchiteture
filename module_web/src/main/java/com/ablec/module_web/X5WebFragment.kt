package com.ablec.module_web

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.tencent.smtt.export.external.interfaces.*
import com.tencent.smtt.sdk.CookieSyncManager
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.ablec.lib.base.BaseFragment
import com.ablec.lib.view.loading.ErrorCallback
import com.ablec.lib.view.loading.LoadingCallback
import com.ablec.module_base.config.ModuleConfig
import com.ablec.module_base.http.BaseHeader
import com.ablec.module_base.web.PROTOCOL_NAME
import com.ablec.module_base.web.WebJavascriptInterfaceBase
import com.ablec.module_web.databinding.ModuleWebFragmentLayoutBinding
import com.ablec.module_web.webview.WebUtils
import com.ablec.module_web.webview.X5WebView
import java.util.*

class X5WebFragment : BaseFragment() {

    private lateinit var mUrl: String
    private lateinit var originalUrl: String

    private lateinit var loading: LoadService<Any>

    private lateinit var binding: ModuleWebFragmentLayoutBinding
    private lateinit var webJavascriptInterfaceBase:WebJavascriptInterfaceBase
    private var isError = false
    private var mIsWebViewLoadComplete = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(KEY_URL)?.let {
            mUrl = it
            originalUrl = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ModuleWebFragmentLayoutBinding.inflate(layoutInflater)
        loading = LoadSir.getDefault().register(
            binding.root
        ) { loadUrl() }
        return loading.loadLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebView(binding.webview)
        loadUrl()
    }

    private fun initWebView(webView: X5WebView) {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (WebUtils.isWhiteList(requireContext(), url)) {
                    return true
                }
                //点击后跳转
                if (url.startsWith(BuildConfig.BASE_URL)) {
                    skipOtherActivity(url)
                    return true
                }
                //拨打电话
                if (url.startsWith("tel:")) {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                    startActivity(intent)
                    return true
                }
                return false
            }

            /**
             * js脚本载入完成后执行
             */
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                if(url == "about:blank"){
                    showView(ERROR_FLAG)
                }else{
                    showView(SUCCESS_FLAG)
                }
            }

            /**
             * https://blog.csdn.net/dreamsever/article/details/71170111
             * js脚本载入之前执行
             */
            override fun onPageCommitVisible(p0: WebView?, p1: String?) {
                super.onPageCommitVisible(p0, p1)
            }

            override fun onPageStarted(webView: WebView, s: String, bitmap: Bitmap?) {
                super.onPageStarted(webView, s, bitmap)
            }

            override fun onReceivedSslError(
                webView: WebView,
                sslErrorHandler: SslErrorHandler,
                sslError: SslError
            ) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError)
                sslErrorHandler.proceed()
            }

            override fun onReceivedError(
                view: WebView, errorCode: Int,
                description: String, failingUrl: String
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                if (TextUtils.equals(failingUrl, mUrl)) {
                    showView(ERROR_FLAG)
                }
            }

            override fun onReceivedHttpError(
                webView: WebView,
                webResourceRequest: WebResourceRequest,
                webResourceResponse: WebResourceResponse
            ) {
                super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse)
                //favicon.ico 资源 加载不成功也会返回404
                if (TextUtils.equals(webResourceRequest.url.toString(), mUrl)) {
                    showView(ERROR_FLAG)
                }
            }

        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(webView: WebView, title: String) {
                super.onReceivedTitle(webView, title)
            }

            override fun onProgressChanged(webView: WebView, newProgress: Int) {
                super.onProgressChanged(webView, newProgress)
                if (activity == null) {
                    return
                }
                if (!mIsWebViewLoadComplete && newProgress != 100) {
                    showView(LOADING_FLAG)
                }
            }

            override fun onJsAlert(p0: WebView?, p1: String?, p2: String?, p3: JsResult?): Boolean {
                ToastUtils.showShort(p1)
                return true
            }
        }
        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        X5WebView.setWebContentsDebuggingEnabled(ModuleConfig.isTestMode())
        setJavascriptInterface(webView)
        CookieSyncManager.createInstance(requireContext())
        CookieSyncManager.getInstance().sync()
    }

    private fun setJavascriptInterface(webView: X5WebView) {
        webJavascriptInterfaceBase = WebJavascriptInterfaceBase(activity, webView)
        lifecycle.addObserver(webJavascriptInterfaceBase)
        webView.addJavascriptInterface(webJavascriptInterfaceBase, PROTOCOL_NAME)
    }

    private fun skipOtherActivity(url: String) {
        WebActivity.start(requireActivity(), url)
    }


    private fun loadUrl() {
        mIsWebViewLoadComplete = false
        val extraHeaders: MutableMap<String, String> = HashMap()
        extraHeaders["JSONPARAMS"] = BaseHeader.getHeader()
        checkUrlPath()
        supplyDeviceName()
        binding.webview.loadUrl(mUrl, extraHeaders)
    }
//https://www.baidu.com/
    private fun supplyDeviceName() {
        mUrl = if (mUrl.contains("?")) {
            "$mUrl&devicename=android"
        } else {
            "$mUrl?devicename=android"
        }
    }

    /**
     *  1、WebViewClient.onPageFinished 如果url只有域名，系统会在域名后面增加"/"符号
     *  2、判断如果url只有域名，手动追加"/"符号
     */
    private fun checkUrlPath(){
        val url =  Uri.parse(mUrl)
        mUrl = if(url.path.isNullOrEmpty())
                "$mUrl/"
              else
                 mUrl
    }

    /**
     * 界面显示
     *
     * @param type
     */
    private fun showView(type: Int) {
        if (isError) {
            loading.showCallback(ErrorCallback::class.java)
            return
        }
        if (type == SUCCESS_FLAG) {
            mIsWebViewLoadComplete = true
            loading.showCallback(SuccessCallback::class.java)
        } else if (type == ERROR_FLAG) {
            mIsWebViewLoadComplete = true
            isError = true
            loading.showCallback(ErrorCallback::class.java)
        } else if (type == LOADING_FLAG) {
            loading.showCallback(LoadingCallback::class.java)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        webJavascriptInterfaceBase.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lifecycle.removeObserver(webJavascriptInterfaceBase)
        binding.webview.destroy()
    }

    companion object {
        const val ERROR_FLAG = 0
        const val SUCCESS_FLAG = 1
        const val LOADING_FLAG = 2

        const val KEY_URL = "KEY_URL"
        fun newInstance(url: String): X5WebFragment {
            val args = Bundle()
            args.putString(KEY_URL, url)
            val fragment = X5WebFragment()
            fragment.arguments = args
            return fragment
        }

    }

}