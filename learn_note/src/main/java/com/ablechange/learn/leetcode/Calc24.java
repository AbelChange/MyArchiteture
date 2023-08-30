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

        int count = 0;
        for (int i = 0; i < f.length; i++) {
            ArrayList<String> fuhao = new ArrayList<>(Arrays.asList(f));
            for (int j = 0; j < fuhao.size(); j++) {
                for (int k = 0; k < fuhao.size(); k++) {
                    for (int l = 0; l < fuhao.size(); l++) {
                        count++;
                        System.out.println(fuhao.get(i) + "" + fuhao.get(j) + "" + fuhao.get(k) + "" + fuhao.get(l));
                    }
                }
            }
        }
        System.out.println(count);
    }


}