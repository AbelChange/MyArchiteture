package com.temption.myarchitecture.learn.interceptorchain

/**
 * @Description:
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/15 13:47
 */
 interface Interceptor {
    fun intercept(chain: Chain): String
}