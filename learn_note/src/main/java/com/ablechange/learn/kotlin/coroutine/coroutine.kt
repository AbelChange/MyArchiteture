package com.ablechange.learn.kotlin.coroutine

import kotlinx.coroutines.*


//MainScope
suspend fun testJob() {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("catch exception ${throwable.message}")
    }

    val sscope = MainScope()

    val scope = CoroutineScope(Job() + exceptionHandler)

    val mainScope = CoroutineScope(SupervisorJob() + exceptionHandler)

    mainScope.launch {
        println("job1 start")
        delay(2000)
        println("job1 end")
    }
    mainScope.launch {
        println("job2 start")
        delay(1000)
//        throw CancellationException("取消异常")
        throw IllegalStateException("其他异常")

        println("job2 end")
    }
    mainScope.launch {
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