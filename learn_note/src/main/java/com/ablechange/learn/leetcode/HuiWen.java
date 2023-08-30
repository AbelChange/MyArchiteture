package com.ablechange.learn.leetcode;

/**
 * @author HaoShuaiHui
 * @description:
 * @date :2023/4/19 11:35
 */
public class HuiWen {

    public static void main(String[] args) {
        String s = "atgccgcgcaacgdagctct";
        huiwen(s);
    }

    private static void huiwen(String s) {
        int max = 0;
        String result = "";
        for (int i = 0; i < s.length() - 1; i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                String sub = s.substring(i, j);
                if (isHuiWen(sub) && sub.length() > max) {
                    max = sub.length();
                    result = sub;
                }
            }
        }
        System.out.println(result);
    }

//会导致一些额外的开销，因为它需要创建一个新的字符串进行比较
//    private static boolean isHuiWen(String s) {
//        return s.equals(new StringBuilder(s).reverse().toString());
//    }


    //双指针法
    private static boolean isHuiWen(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
