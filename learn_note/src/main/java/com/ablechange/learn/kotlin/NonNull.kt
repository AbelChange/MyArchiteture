package com.ablechange.learn.kotlin

import com.google.gson.Gson


val jsonStr = """{}"""

//不是所有参数都有默认值->不会生成无参构造
//gson:使用unsafe方式创建对象，默认值无效（java行为一致），同时绕过了kotlin的空安全监测
//非基本类型使用时可能会导致npe
data class NonNull(val id: String, val age: Int){
    init {
        println("init 函数 在主构造方法之后执行")
    }
}

//所有参数都有默认值->会生成无参构造
//gson：会先使用无参构造，然后赋值，如果为null，则不赋值，默认值有效

 class NullableDefault(val id: String? = "dsad", val age: Int? = 0)

fun main() {
    val default = Gson().fromJson<NonNull>(jsonStr, NonNull::class.java)
    println(default)
}

