package com.ablechange.learn.design.delegate

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2023/3/10 17:18
 */
interface ISubject {
    fun doSth()
}

class Subject : ISubject {
    override fun doSth() {
        println("Subject doSth")
    }
}

class RealSubject : ISubject {
    override fun doSth() {
        println("RealSubject doSth")
    }
}