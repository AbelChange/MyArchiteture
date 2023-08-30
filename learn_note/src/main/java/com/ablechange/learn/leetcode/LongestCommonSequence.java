package com.ablechange.learn.leetcode;

/**
 * @author HaoShuaiHui
 * @description:两个字符串最长公共子串
 * @date :2023/4/19 14:46
 */
public class LongestCommonSequence {
    public static void main(String[] args) {
        String s1 = "1234567";
        String s2 = "trete$32addf$";
        getResult(s1, s2);
    }

    private static void getResult(String s1, String s2) {
        int length1 = s1.length();
        int length2 = s2.length();
        String shorter = s1;
        String longer = s2;
        if (length1 > length2) {
            shorter = s2;
            longer = s1;
        }
        int max = 0;
        String result = "";
        for (int i = 0; i < shorter.length() - 1; i++) {
            for (int j = i + 1; j <= shorter.length(); j++) {
                String s = shorter.substring(i, j);
                if (longer.contains(s) && s.length() > max) {
                    max = s.length();
                    result = s;
                }
            }
        }
        System.out.println(result);
    }
}
