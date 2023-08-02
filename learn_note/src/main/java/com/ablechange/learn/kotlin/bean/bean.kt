package com.ablechange.learn.kotlin.bean

/**
 * @author HaoShuaiHui
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