package com.ablec.myarchitecture.logic.websocket

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2021/11/30 5:08
 */
interface IRetryStrategy {
    fun retry(callback:()->Unit)

    fun reset()
}