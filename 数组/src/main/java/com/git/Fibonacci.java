package com.git;

public class Fibonacci {

    public static void main(String[] args) {

        System.out.println(feb2(7));

        addBinary("1010","1011");
    }

    //  0 1 1 2 3 5 8 13 ......   斐波那契: 第n 个数是 (n-1) + (n-2)
    public static int feb2(int n){
        if(n <= 1) return n;

        int[] febArr = new int[n+1];
        febArr[0] = 0;
        febArr[1] = 1;
        int result = 0;

        for (int i = 2; i <= n; i++) {

            febArr[i] = febArr[i-1] + febArr[i-2];
        }

        return febArr[n];

    }

    //  0 1 1 2 3 5 8 13 ......   斐波那契: 第n 个数是 (n-1) + (n-2)
    public static int feb(int n){

        if(n <= 1 ) return n;

        return feb( n -1) + feb( n -2);
    }


    /**
     * 给你两个二进制字符串，返回它们的和（用二进制表示）。 输入为 非空 字符串且只包含数字 1 和 0。
     *
     * 输入: a = "11", b = "1"
     * 输出: "100"
     *
     * 输入: a = "1010", b = "1011"
     * 输出: "10101"
     * */

    public static String addBinary(String a, String b) {
        char[] aChars = a.toCharArray();
        char[] bChars = b.toCharArray();
        int sum = 0;
        int cube = 1 ;
        for (int i = 0; i < a.length(); i++) {
            if (i > 0)
                cube =  cube * 2;

            sum += Integer.valueOf(aChars[i]+"") * cube;
        }

        cube = 1;

        for (int i = 0; i < b.length(); i++) {
            if (i > 0)
                cube =  cube * 2;
            sum += Integer.valueOf(bChars[i]+"") * cube;
        }

        System.out.println(sum);

        return "";
    }


}
