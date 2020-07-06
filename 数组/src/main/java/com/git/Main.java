package com.git;

public class Main {

    public static void main(String[] args) {

//        System.out.println(tribonacci(25));
        int[] nums = {0,12};
        int[] ints = moveZeroes(nums);
        for (int i = 0; i < ints.length; i++) {

            System.out.println(ints[i]);
        }
    }


    public static int tribonacci(int n) {
        if (n == 0 || n == 1) return n;

        int[] arr = new int[n + 1];
        arr[0] = 0;
        arr[1] = 1;
        arr[2] = 1;
        int tmp = 0;
        for (int i = 3; i <= n; i++) {
            arr[i] = arr[i - 1] + arr[i - 2] + arr[i - 3];
        }

        return arr[n];
    }

    // 0 1 0 3 12
    // 1 0 3 12 0
    // 1 3 12 0 0
    public static int[] moveZeroes(int[] nums) {
        int len = nums.length;
        int num = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j <= i; j++) {
                if (nums[j] == 0){
                    for (int k = j + 1; k < len - num; k++) {
                        nums[k - 1] = nums[k];
                    }
                    num++;
                }
            }
        }

        for (int i = len-num; i < len; i++) {
            nums[i] = 0;
        }

        return nums;
    }
}
