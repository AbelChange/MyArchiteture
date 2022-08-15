package com.ablec.module_base.config

import com.ablec.module_base.BuildConfig

/**
 * 每个模块的初始化,模块间联调
 */
object ModuleConstant {
    const val HEAD_ENCRYPT_KEY = "dm-header^*(+*-/^GFkL-=_)abcdefhg=" //加密KEY

    //    //基础module初始化
    //    private static final String RouterInit = "com.ablec.module_base.config.BaseModule";
    //    private static final String LoginInit = "com.ablec.module_login.config.LoginModule";
    //    private static final String PayInit = "com.ablec.module_pay.config.PayModule";
    //    //主业务模块
    //    //    private static final String MainInit = "com.temption.module_main.MainModule";
    //    public static String[] initModuleNames = {RouterInit,LoginInit,PayInit};

    const val UMENG_APPKEY = "6095fb68c9aacd3bd4c94f7f"

    const val QQ_APP_ID = "101954482"
    const val QQ_APP_SECRET = "c111b378443b0f1aa3d8682cece8807f"

    const val WX_APP_ID = "wx69d32d389f0e854b"
    const val WX_APP_SECRET = "b0e36c278b383e6b5711fb74999294fd"

    //微信剧点App小程序原始ID
    const val WX_APP_MIN_SECRET = "gh_c48651b7f647"

    const val TIME_HOUR = "HH:mm"
    const val ORDER_TIME = "yyyy-MM-dd HH:mm:ss"

    // H5 JS交互
    const val KEY_JS_FUNC = "KEY_JS_FUN"
    const val KEY_JS_REFRESH = "refresh"
    const val KEY_JS_LOCATION = "location"
    const val KEY_JS_ORDER_TIME = "orderTime"

    //微信剧点App小程序剧本详情路径
    const val KEY_WX_MIN_DRAMA_DETAIL_PATH = "pages/play/detail/detail?id="

    object Share {
        /**
         * 以图片方式分享
         */
        val BITMAP = 1

        /**
         * 以web方式分享
         */
        val WEB = 2

        /**
         * 以程序方式分享
         */
        val MIN = 3
    }

    /**
     * H5路由
     */
    object H5 {
        /**
         * 订单详情
         */
        const val ORDER_DETAIL = "${BuildConfig.BASE_URL}wpersion/orderDetail?order_no="
        const val ORDER_DETAIL_FROM_ORDER_TYPE = "&from=order&orderType=1"
        const val ORDER_DETAIL_FROM_REVENUE_TYPE = "&from=order&orderType=2"

        /**
         * 新人福利
         */
        const val NEW_USER = "${BuildConfig.BASE_URL}activity/coupon"

        /**
         * 邀请好友
         */
        const val INVITE_FRIEND = "${BuildConfig.BASE_URL}activity/invite"
    }

    /**
     * SERVER BOOLEAN
     */
    object Bool {
        val YES = "1"
        val NO = "0"
    }

    /**
     * EventBus KEY
     */
    object Event {
        /**
         * 当前选择的组团优惠券
         */
        const val SELECT_COUPON_GROUP_TICKET = "SELECT_COUPON_GROUP_TICKET"

        /**
         * 支付宝认证
         */
        const val ZFB_CERTIFY_CERTIFY_FINISH = "certify_finish"
    }
}