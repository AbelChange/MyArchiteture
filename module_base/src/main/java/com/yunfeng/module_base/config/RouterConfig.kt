package com.yunfeng.module_base.config;

/**
 * 路由链表说明
 */
object Login {
    const val ACCOUNT_BASE = "/account"
    const val LOGIN_ACTIVITY = "$ACCOUNT_BASE/loginactivity"
    const val SHANYAN_LOGIN_ACTIVITY = "$ACCOUNT_BASE/shanyanLoginActivity"
    const val ACCOUNT_SERVICE = "$ACCOUNT_BASE/service"
}

object Pay {
    const val PAY_BASE = "/pay"
    const val PAY_SERVICE = "$PAY_BASE/service"
}

object Web {
    const val WEB_BASE = "/web2"
    const val WEB_SERVICE = "$WEB_BASE/service"
}

object Push {
    const val PUSH_BASE = "/push"
    const val PUSH_SERVICE = "$PUSH_BASE/service"
}

object Main {
    const val BASE = "/main"
    const val APP_INFO_SERVICE = "${BASE}/service"
}

object Mine {
    const val BASE = "/mine"
    const val MINE_ADDRESS = "${BASE}/address"
    const val APP_FEEDBACK = "${BASE}/feedback"
    const val APP_SETTING = "${BASE}/setting"
    const val APP_DM_APPLY = "${BASE}/dmApply"
}

object Order {
    const val ORDER_BASE = "/order"
    const val CONFIRM_ORDER = "${ORDER_BASE}/confirmOrder"
    const val PAY_ORDER = "${ORDER_BASE}/payOrder"
}