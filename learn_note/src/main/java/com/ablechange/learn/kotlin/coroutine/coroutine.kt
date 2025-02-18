package com.ablechange.learn.kotlin.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

//val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
//    println("catch exception ${throwable.message}")
//}
//
val mainScope = MainScope()
//
//val scope = CoroutineScope(Job() + exceptionHandler)
//
//val customScope = CoroutineScope(SupervisorJob() + exceptionHandler)
////MainScope
//suspend fun testJob() {
//    customScope.launch {
//        println("job1 start")
//        delay(2000)
//        println("job1 end")
//    }
//    customScope.launch {
//        println("job2 start")
//        delay(1000)
////        throw CancellationException("取消异常")
//        throw IllegalStateException("其他异常")
//
//        println("job2 end")
//    }
//    customScope.launch {
//        println("job3 start")
//        delay(2000)
//        println("job3 end")
//    }
//
//    delay(3000)
//}
//
//fun main() {
//    runBlocking {
//        testJob()
//    }
//}

fun ss() {
    GlobalScope.launch {
        println("挂起前")
        testSuspend()
        println("挂起后")
    }
}


suspend fun testSuspend() = withContext(Dispatchers.IO) {
    println("挂起中")
}


data class InputState(val isTyping: Boolean, val text: String)

fun main() {
    testUserInputState()
}

/**
 * 用户输入事件的处理
 */
private fun testUserInputState() {
    runBlocking {
        val inputStateFlow = MutableStateFlow(InputState(isTyping = false, text = ""))

        val inputFlow = flow {
            listOf("H", "He", "Hel", "Hell", "Hello, World!").forEach {
                val n = (0..3).random()  // 从0到2的范围内随机选择一个数
                delay(500L * n)
                emit(it)  // 每次发射输入事件
            }
        }

        launch {
            inputFlow
                .onEach { input ->
                    // 每次有新输入，更新为正在输入
                    inputStateFlow.value = InputState(isTyping = true, text = input)
                }
                .debounce(1000) // 停止输入1秒后触发
                .onEach {
                    // 停止输入时更新为停止输入状态
                    inputStateFlow.value = InputState(isTyping = false, text = it)
                }
                .launchIn(this) // 启动协程
        }

        // 监听输入状态
        launch {
            inputStateFlow.collect { state ->
                if (state.isTyping) {
                    println("User is typing: ${state.text}")
                } else {
                    println("User stopped typing, final input: ${state.text}")
                }
            }
        }
    }
}


