package com.ablechange.learn.kotlin.bean

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException

/**
 * @author HaoShuaiHui
 *
 * @description:
 * @date :2023/3/13 14:28
 */
class Person {
    var name: String = ""
    var age: Int = 0
}

interface Fruit{
    fun eat()
    fun size():Int
}

class Apple : Fruit {
    override fun eat() {
        println("苹果")
    }

    override fun size(): Int {
        return 5;
    }
}

class Bannana: Fruit {
    override fun eat() {
        println("香蕉")
    }

    override fun size(): Int {
        return 6
    }

}


fun main() {
    runBlocking {
        launch {
            launch {
                println("子协程运行中...")
                delay(2000)
                println("子协程完成") // ✅ 仍然会执行
            }
            delay(1000)
            throw CancellationException("父协程手动取消")
        }
    }
}
