package com.ablec.myarchitecture.learn.interceptorchain

/**
 * @Description:
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/15 13:50
 */
internal class Major : Interceptor {

    override fun intercept(chain: Chain): String {
        return if (chain.requestDay <= 3) {
            "Major:负责"
        } else {
            println("Major上递")
            chain.proceed()
        }
    }
}