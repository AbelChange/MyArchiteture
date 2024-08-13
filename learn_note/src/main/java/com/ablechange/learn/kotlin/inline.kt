package com.ablechange.learn.kotlin


inline fun testInline(action: () -> Unit) {
    action.invoke()
}

inline fun test(a: Int, crossinline action: () -> Unit,  action2: () -> Unit) {
    println("pre")
    println("after")
    testInline(action)
}


fun main() {
    //inline 避免生成匿名对象，讲lambada平铺在调用处
    testInline {

    }
    //noinline 某个参数取消该效果,可以使用该参数作为对象的特征，传递或返回
    test(1,{
        return@test
    },{

    })
    //cross inline 防止提前返回
}


