package com.ablechange.learn.leetcode;

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2023/4/19 11:35
 */
public class HuiWen {

    public static void main(String[] args) {
        String s = "atgccgcgcaacgdagctct";



    }

    private static boolean isHuiWen(String s) {
        return s.equals(new StringBuilder(s).reverse().toString());
    }


}
