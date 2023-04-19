package com.ablechange.learn.leetcode;

import java.util.Arrays;

/**
 * @author HaoShuaiHui
 * @description:排序
 * @date :2023/4/19 11:00
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] ints = {5, 4, 2, 1, 2, 3, 8};
        selectSort(ints);
        bubbleSort(ints);
        huiWen("dsadsadsadsdsasaaa");
    }

    /**
     * 两两比较
     *
     * @param ints
     */
    private static void bubbleSort(int[] ints) {
        for (int i = 0; i < ints.length; i++) {
            for (int j = i; j < ints.length - 1; j++) {
                if (ints[j] > ints[j + 1]) {
                    int temp = ints[j];
                    ints[j] = ints[j + 1];
                    ints[j + 1] = temp;
                }
            }
        }
        System.out.print(Arrays.toString(ints));
    }

    /**
     * 依次get和后面所有比较
     *
     * @param ints
     */
    private static void selectSort(int[] ints) {
        for (int i = 0; i < ints.length; i++) {
            for (int j = i + 1; j < ints.length; j++) {
                if (ints[i] > ints[j]) {
                    int temp = ints[i];
                    ints[i] = ints[j];
                    ints[j] = temp;
                }
            }
        }
        System.out.print(Arrays.toString(ints));
    }

    private static int huiWen(String a) {
        int max = 0;
        for (int i = 0; i < a.length(); i++) {
            for (int j = i + 1; j < a.length(); j++) {
                String child = a.substring(i, j);
                if (comfort(child)) {
                    max = Math.max(max, child.length());
                }
            }
        }
        return max;
    }

    public static boolean comfort(String str) {
        String reversed = new StringBuilder(str).reverse().toString();
        return reversed.equals(str);
    }


}
