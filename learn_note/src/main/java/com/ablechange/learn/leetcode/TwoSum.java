package com.ablechange.learn.leetcode;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @Description:key作为目标
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/26 18:29
 */
public class TwoSum {
    public static void main(String[] args) {
        int [] arr = {1,5,8,9,11};
        int target = 13;
        //返回和为13的两个下标
        int[] result = new TwoSum().findIndex(arr, target);
        System.out.println(Arrays.toString(result));
    }

     public int [] findIndex(int[] arr, int target) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (hashMap.containsKey(arr[i])){
                return new int[]{i,hashMap.get(arr[i])};
            }
            hashMap.put(target - arr[i],i);
        }
        return null;
    }

}
