package com.yunfeng.lib

import androidx.annotation.Keep

/**
 * notice:该接口所有实现 不能混淆，不要将敏感信息放入
 */
@Keep
interface IBaseModule {
    fun init(application: BaseApplication?)

    /**
     *同意用户协议之后才能进行的初始化
     */
    fun lazyInit(application: BaseApplication?)
}