package com.ablechange.learn.design.delegate

import kotlin.reflect.KProperty

/**
 * kotlin类委托
 * @constructor
 */
class DelegateKt(sub: ISubject) : ISubject by sub {
//Proxy可以覆盖sub成员
//    override fun doSth() {
//
//    }
}

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        val delegateKt = DelegateKt(Subject())
        delegateKt.doSth()  //委托对象实现
    }
}


/**
 * 属性委托
 * @property p Int
 * @constructor
 */
class DelegateProperty(val p: Int) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        println("属性委托$thisRef")
        return p * p
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val l by DelegateProperty(10)
            println(l)
        }
    }

}













