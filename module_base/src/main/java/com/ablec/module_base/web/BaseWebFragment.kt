package com.ablec.module_base.web

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.lifecycle.lifecycleScope
import com.ablec.lib.BaseApplication
import com.ablec.lib.base.BaseFragment
import com.ablec.module_base.BuildConfig
//import com.ablec.lib.util.ImageUtil
import com.blankj.utilcode.util.LogUtils
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.export.external.interfaces.WebResourceError
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.*
import kotlinx.coroutines.launch

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2022/10/12 11:58
 */
abstract class BaseWebFragment(@LayoutRes contentLayoutId: Int) : BaseFragment(contentLayoutId) {
    private val TAG = this.javaClass.simpleName

    private var mWebView: WebView? = null

    private var mIsLoadSuccess = true

    private var multipleMediaCallBack: ValueCallback<Array<Uri>>? = null

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri == null) {
                multipleMediaCallBack?.onReceiveValue(null)
            } else {
                viewLifecycleOwner.lifecycleScope.launch {
//                    val resizeBitmap = ImageUtil.getResizeBitmap(requireContext(), uri)
//                    if (resizeBitmap != null) {
//                        multipleMediaCallBack?.onReceiveValue(arrayOf(resizeBitmap))
//                    } else {
//                        multipleMediaCallBack?.onReceiveValue(arrayOf(uri))
//                    }
                }

            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mWebView = WebView(BaseApplication.instance).apply {
            commonConfig(this)
            configJs(this)
            getWebContainer().addView(
                this, FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
    }

    abstract fun getWebContainer(): ViewGroup

    abstract fun configJs(webView: WebView)

    abstract fun pageState(start: PageState)

    private fun commonConfig(webView: WebView) {
        WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
        webView.apply {
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
            isScrollContainer = false
            overScrollMode = View.OVER_SCROLL_NEVER
        }
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            useWideViewPort = true //将图片调整到适合webview的大小
            loadWithOverviewMode = true //将图片调整到适合webview的大小
            setSupportZoom(false)
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            cacheMode = WebSettings.LOAD_DEFAULT
            javaScriptCanOpenWindowsAutomatically = true
            defaultTextEncodingName = "utf-8"
            loadsImagesAutomatically = true
            mediaPlaybackRequiresUserGesture = false
            setAppCacheEnabled(true)
            cacheMode =WebSettings.LOAD_DEFAULT
            allowContentAccess = true
            allowFileAccess = true
            textZoom = 100
            setAllowFileAccessFromFileURLs(true)
            setAllowUniversalAccessFromFileURLs(true)
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(
                view: WebView,
                url: String?,
                favicon: Bitmap?
            ) {
                super.onPageStarted(view, url, favicon)
                LogUtils.d(TAG, "onPageStarted${view.url}")
                pageState(PageState.START)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                LogUtils.d(TAG, "onPageFinished:webView.url=${url}")
                if (mIsLoadSuccess) {
                    pageState(PageState.SUCCESS)
                } else {
                    pageState(PageState.ERROR)
                }
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                LogUtils.d(TAG, "onReceivedError${view?.url}")
//                mWebView?.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
                //show Custom Error
                if (request.isForMainFrame) {
                    mIsLoadSuccess = false
                }
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                LogUtils.d(TAG, "shouldOverrideUrlLoading${request?.url}")
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onReceivedSslError(
                p0: WebView?,
                p1: SslErrorHandler,
                p2: SslError?
            ) {
//                super.onReceivedSslError(p0, p1, p2)
                // 接受所有网站的证书
                p1.proceed()
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        mWebView?.apply {
            loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            clearHistory()
            val viewGroup = parent as ViewGroup
            viewGroup.removeView(mWebView)
            destroy()
        }
        mWebView = null
    }


    enum class PageState {
        START, SUCCESS, ERROR
    }


}