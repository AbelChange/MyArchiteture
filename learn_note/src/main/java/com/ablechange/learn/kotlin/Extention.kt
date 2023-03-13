package com.ablechange.learn.kotlin

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2023/3/13 14:35
 */


/**
 * 扩展函数理解成静态方法，属于类的概念
 */


/**
 * 重载方法：静态调度，扩展函数也是静态调度
 */
fun test() {
    val a: Fruit = Apple()
    A().foo(a)
}

class A {
    fun foo(fruit: Fruit) {

    }

    fun foo(fruit: Apple) {

    }
}


