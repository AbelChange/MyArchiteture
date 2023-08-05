package com.ablec.module_base.config;

/**
 * 路由链表说明
 */
object Login {
    const val ACCOUNT_BASE = "/account"
    const val ACCOUNT_SERVICE = "$ACCOUNT_BASE/service"
}

object Compose{
    const val COMPOSE_BASE = "/composes"
    const val COMPOSE_ACTIVITY = "$COMPOSE_BASE/composeActivity"
    const val COMPOSE_SERVICE = "$COMPOSE_BASE/service"
}


object Pay {
    const val PAY_BASE = "/pay"
    const val PAY_SERVICE = "$PAY_BASE/service"
}


object Main {
    const val BASE = "/main"
    const val APP_INFO_SERVICE = "${BASE}/service"
}

