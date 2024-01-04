package com.ablechange.learn.kotlin

import com.google.gson.Gson

//gson解析导致的默认值失效
//原因：存在参数未给默认值，导致没有无参构造，默认值无效
val jsonStr = """{}"""

//存在参数没有默认值->不会生成无参构造
//gson:使用unsafe方式创建对象，默认值无效（java行为一致），同时绕过了kotlin的空安全监测
//非基本类型使用时可能会导致npe
data class NonNull(val id: String="defaultStr", val age: Int) {
    init {
        println("init 函数 在主构造方法之后执行")
    }
}

//所有参数都有默认值->生成无参构造
//先使用无参构造，然后赋值
//只要存在某个参数没有给默认值，就会导致不生成 无参构造，导致其余默认值不生效
data class NullableDefault(val id: String? = null, val age: Int? = -1)

fun main() {
    val default = Gson().fromJson<NullableDefault>(jsonStr, NullableDefault::class.java)
    println(default)
}

