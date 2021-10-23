package com.temption.myarchitecture.learn.interceptorchain

/**
 * @Description:
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/15 14:59
 */
class Chain(private val interceptors: List<Interceptor>,
            private val index: Int,
            val requestDay: Int) {
    fun proceed(): String {
        //拦截器拦截Chain
        return interceptors[index].intercept(Chain(interceptors, index + 1, requestDay))
    }
}