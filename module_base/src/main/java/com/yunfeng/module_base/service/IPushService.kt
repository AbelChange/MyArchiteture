package com.yunfeng.module_base.service

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/5/19 14:03
 */
interface IPushService {
    fun notifyMsgCome()
    fun registerObserver(observer: Observer?)
    fun unregisterObserver(observer: Observer?)
    interface Observer {
        fun onMsgCome()
    }
}