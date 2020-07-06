package 数组;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class 两数之和 {
    public static int[] twoSum(int[] nums, int target) {
        for(int i =0; i < nums.length;i++){
            for(int j =0; i< i;j++){
                if(nums[i] + nums[j] == target){
                    int[] result = new int[2];
                    result[0] = j;
                    result[1] = i;
                    return result;
                }
            }
        }
        return null;
    }

    public static int[] twoSum2(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();

        for (int i = 0; i < nums.length; i++) {
            if(map.containsKey(target - nums[i])){
                return new int[]{i,map.get(target - nums[i])};
            }
            map.put(nums[i],i);
        }

        return null;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 7, 11, 15};
        int[] ints = twoSum2(arr, 9);
        System.out.println(ints[0]);
        System.out.println(ints[1]);
    }
}
