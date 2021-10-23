package com.yunfeng.module_login.config

import android.util.Log
import com.blankj.utilcode.util.ProcessUtils
import com.chuanglan.shanyan_sdk.OneKeyLoginManager
import com.yunfeng.lib.BaseApplication
import com.yunfeng.lib.IBaseModule
import com.yunfeng.module_base.service.RouterServiceManager

/**
 * 组件通信模块初始化
 *
 * @author haoshuaihui
 */
class LoginModule : IBaseModule {

    override fun init(application: BaseApplication?) {
    }

    companion object {
        val TAG: String = javaClass.name
        private var shanyanInitStatus = LoginConstant.ShanYanInitStatus.waitSYInit

        /**
         * 闪验是否可用
         */
        var syLoginIsValid: Boolean = false
            get() {
                return shanyanInitStatus == LoginConstant.ShanYanInitStatus.canSYLogin
            }

        /**
         * 拿取闪验的预取号
         *
         * {"innerCode":200002,"innerDesc":"无法识别sim卡或没有sim卡","message":"getPhoneInfo(){\"resultCode\":\"200002\",\"resultDesc\":\"无法识别sim卡或没有sim卡\",\"traceId\":\"e4ce740ce00e40e181b8b7ecfacb5f4f\"}"}
         *{"innerCode":102001,"innerDesc":"选择流量通道失败","message":"_code=1_msg=选择流量通道失败_status=102001_response=_seq=A3318aGy1J855xDJ8Wq5"}
         *
         */

        fun fetchShanYanPhoneInfo(shanyanPhoneInfoCallBack: ShanyanPhoneInfoCallBack? = null) {
            OneKeyLoginManager.getInstance().getPhoneInfo { code: Int, result: String ->
                if (code == LoginConstant.ShanYanCode.READ_PHONE_SUCCESS) {
                    shanyanInitStatus = LoginConstant.ShanYanInitStatus.canSYLogin
                } else {
                    shanyanInitStatus = LoginConstant.ShanYanInitStatus.noSYLogin
                }
                shanyanPhoneInfoCallBack?.phoneInfoStatus(code)
                Log.d(TAG, "SYSDK Step 2:getPhoneInfo:code:$code===result:$result")
            }
        }

        interface ShanyanPhoneInfoCallBack {
            fun phoneInfoStatus(status: Int)
        }
    }

    override fun lazyInit(application: BaseApplication?) {
        if (!ProcessUtils.isMainProcess()) {
            return
        }
        application?.let {
            OneKeyLoginManager.getInstance().setDebug(true)
            OneKeyLoginManager.getInstance()
                .init(application, LoginConstant.SY_APPID) { code: Int, result: String? ->
                    if (code == LoginConstant.ShanYanCode.INIT_SUCCESS) {
                        if (RouterServiceManager.getAccountService()?.isLogin() == true) {
                            return@init
                        }
                        fetchShanYanPhoneInfo()
                    } else {
                        shanyanInitStatus = LoginConstant.ShanYanInitStatus.noSYLogin
                    }
                }
        }
    }
}