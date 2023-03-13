package com.ablechange.learn.kotlin

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2023/3/13 14:28
 */
interface Fruit{
    fun eat()
    fun size():Int
}

class Apple :Fruit{
    override fun eat() {
        println("苹果")
    }

    override fun size(): Int {
        return 5;
    }
}

class Bannana:Fruit{
    override fun eat() {
        println("香蕉")
    }

    override fun size(): Int {
        return 6
    }

}