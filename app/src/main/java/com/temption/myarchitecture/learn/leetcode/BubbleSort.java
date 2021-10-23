package com.temption.myarchitecture.learn.leetcode;

/**
 * @Description:
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/27 15:26
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {1, 5, 7, 8, -1, -3};
        bubbleSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ">");
        }
    }

    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}
