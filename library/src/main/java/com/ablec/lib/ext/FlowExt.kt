package com.ablec.lib.ext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2022/3/7 9:40
 */

fun <T> Flow<T>.throttleFirst(thresholdMillis: Long): Flow<T> = flow {
    var lastTime = 0L // 上次发射数据的时间
    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime > thresholdMillis) {
            lastTime = currentTime
            emit(upstream)
        }
    }
}
