package com.temption.myarchitecture.learn.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description:
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/27 13:19
 */
public class PhoneNo {

    public static List<String> result = new ArrayList<String>();
    public static HashMap<Integer, String> map = new HashMap<Integer, String>();


    public static void main(String[] args) {
        System.out.println(letterCombinations("23"));
    }


    private static int char2Int(char param) {
        return param - '0';
    }


    /**
     * @param digits
     * @return
     */
    public static List<String> letterCombinations(String digits) {
        map.put(2, "abc");
        map.put(3, "def");
        map.put(4, "ghi");
        map.put(5, "jkl");
        map.put(6, "mno");
        map.put(7, "pqrs");
        map.put(8, "tuv");

        if (digits == null || digits.length() < 1) {
            return result;
        }

        dfs(digits, 0, "");
        return result;
    }

    private static void dfs(final String digits, int index, String str) {
        if (index == digits.length()) {
            result.add(str);
            return;
        }
        //对应按键上的字母
        String value = map.get(char2Int(digits.charAt(index)));
        for (int i = 0; i < value.length(); i++) {
            dfs(digits, index + 1, str + value.charAt(i));
        }
    }


}
