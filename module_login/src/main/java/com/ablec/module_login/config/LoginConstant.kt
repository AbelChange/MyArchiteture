package com.ablec.module_login.config


/**
 *
 * Created by yangrong on 2021/5/24.
 */
class LoginConstant {

    /**
     * 闪验一键登录验证码
     */
    interface ShanYanCode {
        companion object {
            //登录成功
            const val SUCCESS = 1000
            const val INIT_SUCCESS = 1022

            /**
             * 手动点击返回按键
             */
            const val USER_CANCEL = 1011

            /**
             * 闪验预取号成功
             */
            const val READ_PHONE_SUCCESS = 1022
            const val NO_SIM_CARD = 1023
        }
    }

    /**
     * 闪验初始化的状态
     */
    interface ShanYanInitStatus {
        companion object {
            /**
             * 可以闪验登录
             */
            const val canSYLogin = 1
            const val noSYLogin = -1
            const val waitSYInit = 0
        }
    }

    companion object {
        const val SY_APPID = "6KjfrCu7"
    }

    interface LoginActionCommand {
        companion object {
            /**
             * 跳转到登录界面，展示验证码登录界面
             */
            const val loginByPhoneCode = 1

            /**
             * 跳转到登录界面，展示手机号密码登录界面
             */
            const val loginByPhonePassword = 2

            /**
             * 利用QQ登录
             */
            const val loginByQQ = 3

            /**
             * 利用微信登录
             */
            const val loginByWeiXin = 4
        }
    }


    object Bool {
        val YES = "1"
        val NO = "0"
    }

    object Bus {
        const val KEY_THIRD_LOGIN = "KEY_THIRD_LOGIN"
    }
}