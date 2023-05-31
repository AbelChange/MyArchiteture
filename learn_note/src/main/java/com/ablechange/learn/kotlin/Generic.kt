package com.ablechange.learn.kotlin

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2023/3/13 11:11
 */
fun main(args: Array<String>) {
    //一、为何java声明集合可以不指定泛型？

    //kotlin声明list必须指定泛型，而java不需要
    //因为 java1.5 才引入泛型，为了兼容之前的
//    List list = new ArrayList();
//    list.add("1");
//    list.add(2);

    //kotlin则没有这个问题,因此必须声明泛型
    val fruits = listOf<Fruit>()
    val fruits2 = mutableListOf<Fruit>()
    val arrayList = arrayListOf("one", "two")//类型推导


    //二、泛型擦除导致无法直接获取类型信息，该如何获取呢?
    val list1 = ArrayList<String>()
    println(list1.javaClass.genericSuperclass)//java.util.AbstractList<E>
    //1.使用匿名内部类
    val list2 = object : ArrayList<String>() {} //匿名内部类 --》gson传递泛型参数
    println(list2.javaClass.genericSuperclass)//java.util.ArrayList<java.lang.String>
    //2.使用内联函数,reified不能被java调用
    getType<List<String>>()


    //三、kotlin List支持协变 List<out E> 可读不可写
    val appletS: List<Fruit> = listOf<Apple>()

    val numbers: List<Number> = listOf<Int>()


    val listOf = listOf<Apple>();
    add(listOf)


}


fun add(fr: List<Fruit>) {


}

//inline属于编译器优化，减少高阶函数带来 堆栈层级的问题
inline fun <reified T> getType(): Class<T> {
    return T::class.java
}

class FruitPlate<T : Fruit>(val t: T)//上界约束（where 多个上界）


