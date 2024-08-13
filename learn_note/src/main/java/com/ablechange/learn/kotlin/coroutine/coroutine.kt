package com.ablechange.learn.kotlin.coroutine

import kotlinx.coroutines.*

val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    println("catch exception ${throwable.message}")
}

val mainScope = MainScope()

val scope = CoroutineScope(Job() + exceptionHandler)

val customScope = CoroutineScope(SupervisorJob() + exceptionHandler)
//MainScope
suspend fun testJob() {
    customScope.launch {
        println("job1 start")
        delay(2000)
        println("job1 end")
    }
    customScope.launch {
        println("job2 start")
        delay(1000)
//        throw CancellationException("取消异常")
        throw IllegalStateException("其他异常")

        println("job2 end")
    }
    customScope.launch {
        println("job3 start")
        delay(2000)
        println("job3 end")
    }

    delay(3000)
}

fun main() {
    runBlocking {
        testJob()
    }
}

