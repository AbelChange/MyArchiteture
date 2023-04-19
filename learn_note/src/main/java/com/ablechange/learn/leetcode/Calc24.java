package com.ablechange.learn.leetcode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2023/4/19 14:46
 */
public class Calc24 {

    public static void main(String[] args) {
        int[] arr = new int[]{2, 5, 8, 9}; //24 种顺序

        String[] f = new String[]{"+", "-", "*", "/"};
//
//        for (int i = 0; i < arr.length; i++) {
//            for (int j = 0; j < arr.length; j++) {
//                for (int k = 0; k < arr.length; k++) {
//                    for (int l = 0; l < arr.length; l++) {
//                        if (i == j || i == k || i == l || j == k || j == l || k == l) {
//                            continue;
//                        }
//                        System.out.println(arr[i] + "" + arr[j] + "" + arr[k] + "" + arr[l]);
//                    }
//                }
//
//            }
//        }


        int count = 0;
        for (int i = 0; i < f.length; i++) {
            ArrayList<String> fuhao = new ArrayList<>(Arrays.asList(f));
            fuhao.remove(i);
            for (int j = 0; j < fuhao.size(); j++) {
                for (int k = 0; k < fuhao.size(); k++) {
                    for (int l = 0; l < fuhao.size(); l++) {
                        count ++ ;
                        System.out.println(fuhao.get(j) + "" + fuhao.get(k) + "" + fuhao.get(l));
                    }
                }
            }
        }
        System.out.println(count);
    }


}