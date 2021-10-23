package com.temption.myarchitecture.learn.interceptorchain

import java.util.*

/**
 * @Description:
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/15 13:49
 */
class Programmer {

    fun requestHoliday(): String {
        val interceptors = ArrayList<Interceptor>()
        interceptors.add(Major())
        interceptors.add(Ceo())
        interceptors.add(Boss())
        val chain = Chain(interceptors, 0, 7)
        return chain.proceed()
    }
}