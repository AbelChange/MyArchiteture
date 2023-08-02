package com.ablec.myarchitecture.logic.flow

import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


private class SafeFlow<T>(private val block: () -> Unit) : AbstractFlow<T>() {
    override suspend fun collectSafely(collector: FlowCollector<T>) {
        block()
    }
}

class FLw {
    private val scope = MainScope()
    fun produce() {
        scope.launch {
            flow<Int> {
                emit(1);
            }.filter {
                true
            }.collect {

            }
        }
    }

}

