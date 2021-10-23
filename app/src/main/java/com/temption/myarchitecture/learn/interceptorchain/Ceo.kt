package com.temption.myarchitecture.learn.interceptorchain

/**
 * @Description:
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/15 13:58
 */
class Ceo : Interceptor {

    override fun intercept(chain: Chain): String {
        return if (chain.requestDay <= 5) {
            "Ceo:负责"
        } else {
            println("Ceo:上递")
            chain.proceed()
        }
    }
}