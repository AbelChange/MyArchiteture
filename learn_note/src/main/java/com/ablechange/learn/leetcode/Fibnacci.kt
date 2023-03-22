package com.ablechange.learn.leetcode


fun main(args: Array<String>) {
    print(Fibnacci().test(5))
}

class Fibnacci {
    fun test(i: Int): Int {
        if (i <= 0) {
            return 0;
        }
        if (i == 1) {
            return 1
        }
        if (i == 2) {
            return 2;
        }
        return test(i - 1) + test(i - 2);
    }
}