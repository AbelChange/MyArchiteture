package com.ablechange.learn.kotlin


inline fun test2(action: () -> Unit) {
    action.invoke()
}

inline fun test(a: Int, crossinline action: () -> Unit, noinline action2: () -> Unit) {
    println("pre")
    println("after")
    test2(action)
}

fun main() {
    //inline 避免生成匿名对象，讲lambada平铺在调用处
    test2 {

    }
    //noinline 某个参数取消该效果,可以使用该参数作为对象的特征，传递或返回
    test(1,{
        return@test
    },{

    })
    //cross inline 防止被 高阶函数 提前返回
}


