package com.ablec.module_base.service

import com.alibaba.android.arouter.facade.template.IProvider


/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/5/19 14:03
 */
interface IPayService : IProvider {
    fun registerObserver(observer: Observer?)
    fun unregisterObserver(observer: Observer?)
    fun notifyPaySuccess()
    fun notifyPayCancel()
    fun notifyPayFailure(reason: String?)
    interface Observer {
        fun onPaySuccess()
        fun onPayCancel()
        fun onPayFailure(reason: String?)
    }

    enum class PAY_TYPE {
        ALIPAY, WXPAY
    }
}