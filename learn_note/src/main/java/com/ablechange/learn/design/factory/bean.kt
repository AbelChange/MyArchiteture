package com.ablechange.learn.design.factory

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2023/3/13 15:00
 */

interface IFactory {
    fun produce(): IProduct
}

class KongKeFactory : IFactory {
    override fun produce(): IProduct {
        return Plane("空客")
    }
}

class BaoMaFactory : IFactory {
    override fun produce(): IProduct {
        return Car("宝马")
    }
}

class Plane(private val brand: String) : IProduct {
    override fun applyResult() {
        println("${brand}飞机飞起来")
    }
}

class Car(private val brand: String) : IProduct {
    override fun applyResult() {
        println("${brand}汽车跑起来")
    }
}
