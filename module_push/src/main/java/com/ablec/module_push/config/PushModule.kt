package com.ablec.module_push.config

import android.text.TextUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.RomUtils
import com.tencent.android.tpush.XGIOperateCallback
import com.tencent.android.tpush.XGPushConfig
import com.tencent.android.tpush.XGPushManager
import com.ablec.lib.BaseApplication
import com.ablec.lib.IBaseModule
import com.ablec.module_base.config.ModuleConfig.isTestMode
import com.ablec.module_base.service.IAccountService
import com.ablec.module_base.service.RouterServiceManager
import com.ablec.module_push.BuildConfig

/**
 * 组件通信模块初始化
 * @author haoshuaihui
 */
class PushModule : IBaseModule {

    override fun init(application: BaseApplication?) {
    }

    override fun lazyInit(application: BaseApplication?) {
        if (!ProcessUtils.isMainProcess()) {
            return
        }
        XGPushConfig.enableDebug(application, BuildConfig.DEBUG)
        enableThirdByRom(application)
        XGPushConfig.enablePullUpOtherApp(application, false)
        XGPushManager.registerPush(application, object : XGIOperateCallback {
            override fun onSuccess(o: Any, i: Int) {
                LogUtils.d("注册成功，设备token为：$o i=$i")
                bindXGPush(application)
            }

            override fun onFail(o: Any, i: Int, s: String) {
                LogUtils.e("注册失败，错误码：$i,错误信息：$s")
            }
        })

        RouterServiceManager.getAccountService()
            ?.registerObserver(object : IAccountService.Observer {
                override fun onLoginSuccess() {
                    bindXGPush(application)
                }

                override fun onLoginCancel() {
                }

                override fun onLoginFailure() {
                }

                override fun onLogout() {
                    XGPushManager.delAllAccount(application)
                }

                override fun onLogoff() {
                    XGPushManager.delAllAccount(application)
                }
            })
    }

    private fun enableThirdByRom(application: BaseApplication?) {
        //打开第三方推送 TAG: OtherPushClient
        XGPushConfig.enableOtherPush(application, true)
        if (RomUtils.isHuawei()) {
            //√ 华为: 配置文件agconnect-services.json 放到 app下
            //app build.gradle 插件：com.huawei.agconnect
        } else if (RomUtils.isXiaomi()) {
            //√ 小米
            XGPushConfig.setMiPushAppId(application, "2882303761519962667")
            XGPushConfig.setMiPushAppKey(application, "5501996220667")
        } else if (RomUtils.isMeizu()) {
            //魅族 ? 没手机
            XGPushConfig.setMzPushAppId(application, "3371962")
            XGPushConfig.setMzPushAppKey(application, "7cb2724ab9cf48b39ce28389545d1db8")
        } else {
            //√ vivo  build.gradle manifest placeholder 中
        }
    }

    fun bindXGPush(application: BaseApplication?) {
        val uid: String? = RouterServiceManager.getAccountService()?.getUserId()
        if (TextUtils.isEmpty(uid)) {
            return
        }
        var account = uid
        if (isTestMode()) {
            account = uid + "_test"
        }
        XGPushManager.bindAccount(application, account, object : XGIOperateCallback {
            override fun onSuccess(o: Any, i: Int) {
                LogUtils.d("绑定" + account + "成功：obj=" + o + " i=" + i)
            }

            override fun onFail(o: Any, errCode: Int, msg: String) {
                LogUtils.e("绑定" + account + "失败：obj=" + o + " errCode=" + errCode + " msg=" + msg)
            }
        })
    }
}