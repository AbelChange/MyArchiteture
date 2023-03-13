package com.ablechange.learn.design.factory

import kotlin.reflect.KClass

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2023/3/10 17:17
 */

fun main(args: Array<String>) {
    AbsFactory.produce(Plane::class).applyResult()
}

interface IProduct {
    fun applyResult()
}

abstract class AbsFactory : IFactory {
    companion object {
        inline fun <reified T : IProduct> produce(kClass: KClass<T>): IProduct {
            return when (kClass) {
                Plane::class -> KongKeFactory().produce()
                Car::class -> BaoMaFactory().produce()
                else -> throw java.lang.IllegalArgumentException()
            }
        }
    }
}













