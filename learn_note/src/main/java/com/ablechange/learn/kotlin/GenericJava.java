package com.ablechange.learn.kotlin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2023/3/13 11:15
 */
public class GenericJava {
    public static void main(String[] args) {
        //java1.5引入泛型，所以这样声明没有问题
        List list = new ArrayList();
        list.add("1");
        list.add(2);


        Apple[] apples = new Apple[10];
        Fruit[] fruits = apples;
        fruits[0] = new Apple();//编译通过，运行报错ArrayStoreException
        System.out.println("测试"+ fruits[0]);
        //编译报错 因为 数组是协变的，而list是不变的
       // List<Fruit> fruitsList= new ArrayList<Apple>();
    }



}
