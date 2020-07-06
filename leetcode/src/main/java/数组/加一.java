package 数组;

import java.util.Arrays;

public class 加一 {

    /*public static int[] plusOne(int[] digits) {
        int len = digits.length;
        int sum = 0;
        int num = 1;
        for (int i = 0; i < len; i++) {
            sum += digits[i] * num;
            num *= 10;
        }
        sum += 1;
        int i = sum  % num;
        if(i == 0){
            int[] arr = new int[len + 1];
            arr[0] = 1;
            digits = arr;
        }else {
            for (int j = 0; j < len; j++) {

            }
        }


        return digits;
    }*/

    /*public static int[] plusOne(int[] digits) {
        int len = digits.length;
        int sum = digits[len - 1] + 1;
        int num = -1;
        if (sum % 10 == 0){
            num = len - 1;        // 记录位置
            for (int i = len - 2; i >= 0 ; i--) {
                sum = digits[i] + 1;
                if (sum % 10 != 0){
                    continue;
                }else {
                    num = i;
                }

            }
        }
        if(num == 0 && digits[0] == 9){
            digits = new int[len + 1];
            digits[0] = 1;
        }else if (num != -1){
            for (int i = num; i < len; i++) {
                digits[i] = 0;
            }
            digits[num - 1] += 1;

        }else {
            digits[len - 1] += 1;
        }

        return digits;
    }*/
    public static int[] plusOne(int[] digits) {

        int len = digits.length;
        for (int i = len - 1; i >= 0 ; i--) {
            int num = digits[i] + 1;
            if(num == 10 && i == 0){
                int[] arr = new int[len + 1];
                arr[0] = 1;
                digits = arr;
                return digits;
            }
            if(num !=  10){
                digits[i] = num;
                return digits;
            } else {
                digits[i] = 0;
            }
        }
        return  null;
    }

        public static void main(String[] args) {
        // [2,5,0,0,0]
        int[] arr = new int[]{9,9,9};
        int[] ints = plusOne(arr);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }
    }
}
