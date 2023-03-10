package com.ablechange.learn.leetcode

/**
 * @Description:
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/27 15:26
 */

fun main(args: Array<String>) {
    val arr = intArrayOf(1, 5, 7, 8, -1, -3)
    BubbleSort().bubbleSort(arr)
    for (i in arr.indices) {
        print(arr[i].toString() + ">")
    }
}

class BubbleSort {
    fun bubbleSort(arr: IntArray) {
        for (i in arr.indices) {
            for (j in 0 until arr.size - 1 - i) {
                if (arr[j] > arr[j + 1]) {
                    val temp = arr[j + 1]
                    arr[j + 1] = arr[j]
                    arr[j] = temp
                }
            }
        }
    }
}