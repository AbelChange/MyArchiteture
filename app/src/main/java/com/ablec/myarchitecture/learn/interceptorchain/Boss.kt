package com.ablec.myarchitecture.learn.interceptorchain

/**
 * @Description:
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/15 13:59
 */
internal class Boss : Interceptor {

    override fun intercept(chain: Chain): String {
        return if (chain.requestDay <=10) {
            "boss:负责"
        } else {
            "boss:滚吧"
        }
    }
}