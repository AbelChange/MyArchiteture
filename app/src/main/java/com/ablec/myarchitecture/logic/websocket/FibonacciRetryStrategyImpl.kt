package com.ablec.myarchitecture.logic.websocket

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import java.util.concurrent.TimeUnit

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2021/11/30 5:09
 */

class FibonacciRetryStrategyImpl : IRetryStrategy {

    private var lastTimeInterval: Long = 0L
    private var currentTimeInterval: Long = 1L
    private var subscriber: DisposableSubscriber<Long>? = null

    override fun retry(callback:()->Unit) {
        var retryInterval = lastTimeInterval + currentTimeInterval
        lastTimeInterval = currentTimeInterval
        currentTimeInterval = retryInterval

        if (retryInterval > MAX_INTERVAL) {
            retryInterval = MAX_INTERVAL
        }

        if (retryInterval < 0){
            retryInterval = 1L
        }

        subscriber?.dispose()
        subscriber = Flowable.intervalRange(0, retryInterval, 0, 1, TimeUnit.SECONDS)
            .subscribeWith(object : DisposableSubscriber<Long>() {
                override fun onComplete() {
                    callback.invoke()
                }

                override fun onNext(t: Long?) {}
                override fun onError(t: Throwable?) {}
            })
    }

    override fun reset() {
        lastTimeInterval = 0
        currentTimeInterval = 1
        subscriber?.dispose()
        subscriber = null
    }

    companion object {
        private const val MAX_INTERVAL = 60L   //即一分钟
    }
}
