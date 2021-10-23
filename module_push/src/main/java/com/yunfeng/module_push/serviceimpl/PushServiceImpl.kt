package com.yunfeng.module_push.serviceimpl

import com.sankuai.waimai.router.annotation.RouterService
import com.yunfeng.module_base.config.Push.PUSH_SERVICE
import com.yunfeng.module_base.service.IPushService
import java.util.concurrent.CopyOnWriteArraySet

/**
 * @Description: 支付service
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/30 13:24
 */
@RouterService(interfaces = [IPushService::class], key = [PUSH_SERVICE], singleton = true)
class PushServiceImpl : IPushService {

    private val mObservers: MutableSet<IPushService.Observer> = CopyOnWriteArraySet()

    override fun registerObserver(observer: IPushService.Observer?) {
        observer?.let { mObservers.add(it) }
    }

    override fun unregisterObserver(observer: IPushService.Observer?) {
        observer?.let { mObservers.remove(it) }
    }

    override fun notifyMsgCome() {
        mObservers.forEach {
            it.onMsgCome()
        }
    }
}