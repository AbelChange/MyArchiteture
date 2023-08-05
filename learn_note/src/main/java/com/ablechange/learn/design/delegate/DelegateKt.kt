package com.ablechange.learn.design.delegate

import java.lang.IllegalStateException
import javax.swing.text.View
import kotlin.properties.Delegates.notNull
import kotlin.properties.Delegates.observable
import kotlin.properties.Delegates.vetoable
import kotlin.properties.ReadWriteProperty
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
class DelegateProperty(val p: Int):ReadWriteProperty<Any?,Int> {

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        println("属性委托$thisRef")
        return p * p
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val l by DelegateProperty(10)
            val b by lazy {
                5
            }
            val mobile by vetoable<String>("dsa") { property: KProperty<*>, oldValue: String, newValue: String ->
                if (newValue.equals("Dsa")) {
                    throw IllegalStateException("校验失败")
                } else {
                    true
                }
            }

            var nnt by notNull<String>()

            var ttn by observable<String>("dsa") { property: KProperty<*>, oldValue: String, newValue: String ->
            }




            println(l)
        }
    }

}













