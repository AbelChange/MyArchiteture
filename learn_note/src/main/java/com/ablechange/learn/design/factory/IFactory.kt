package com.ablechange.learn.design.factory

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2023/3/10 17:17
 */

fun main(args: Array<String>) {
    Factory.produce("飞机").applyResult()
}

interface IFactory {
    fun produce(arg: String): IProduct
}

interface IProduct {
    fun applyResult()
}

object Factory : IFactory {

    override fun produce(arg: String): IProduct {
        if (arg == "飞机") {
            return Plane()
        } else if (arg == "汽车") {
            return Car()
        }
        return object : IProduct {
            override fun applyResult() {
                println("生产不出来")
            }
        }
    }
}

class Plane : IProduct {
    override fun applyResult() {
        println("飞机飞起来")
    }
}

class Car : IProduct {
    override fun applyResult() {
        println("汽车跑起来")
    }
}













