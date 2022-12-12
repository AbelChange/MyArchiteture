package com.ablec.module_base.web

import android.content.Context
import android.util.Log
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.TbsListener

/**
 * 隐私协议同意之后再初始化
 */
object X5WebViewInitializer {

    private var isInstalled = false

    var tbsListener: TbsListener? = null

    fun init(context: Context) {
        Log.e(TAG, "init: ")
        QbSdk.initTbsSettings(
            mapOf<String, Boolean>(
                TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER to true,
                TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE to true
            )
        )
        //设置允许移动网络下进行内核下载。默认不下载，会导致部分一直用移动网络的用户无法使用x5内核
        QbSdk.setDownloadWithoutWifi(true)

        /* SDK内核初始化周期回调，包括 下载、安装、加载 */
        QbSdk.setTbsListener(object : TbsListener {
            /**
             * @param stateCode 110: 表示当前服务器认为该环境下不需要下载；200下载成功
             */
            override fun onDownloadFinish(stateCode: Int) {
                Log.e(TAG, "onDownloadFinish  stateCode = $stateCode")
                tbsListener?.onDownloadFinish(stateCode)
            }

            /**
             * @param stateCode 200、232安装成功
             */
            override fun onInstallFinish(stateCode: Int) {
                Log.e(TAG, "onInstallFinish  stateCode = $stateCode")
                tbsListener?.onInstallFinish(stateCode)
                if (stateCode == 200) {
                    isInstalled = true
                }
            }

            /**
             * 首次安装应用，会触发内核下载，此时会有内核下载的进度回调。
             * @param progress 0 - 100
             */
            override fun onDownloadProgress(progress: Int) {
                Log.i(TAG, "Core Downloading: $progress")
                tbsListener?.onDownloadProgress(progress)
            }
        })


        //此过程包括X5内核的下载、预初始化，接入方不需要接管处理x5的初始化流程，希望无感接入
        QbSdk.initX5Environment(context, object : QbSdk.PreInitCallback {

            override fun onCoreInitFinished() {
                // 内核初始化完成，可能为系统内核，也可能为系统内核
                Log.e(TAG, "onCoreInitFinished: ")
            }

            /**
             * 预初始化结束
             * 由于X5内核体积较大，需要依赖网络动态下发，所以当内核不存在的时候，默认会回调false，此时将会使用系统内核代替
             * @param isX5 是否使用X5内核
             */
            override fun onViewInitFinished(isX5: Boolean) {
                Log.e(TAG, "onViewInitFinished:  $isX5")
                if (isX5) {
                    isInstalled = true
                }
            }
        })

    }


    const val TAG = "X5Init"
}