package 数组;

import java.util.*;

/**
 * 给你一个未排序的整数数组，请你找出其中没有出现的最小的正整数。
 */
public class 缺失的第一个正数 {

    public static int firstMissingPositive(int[] nums) {
        int num = 1;    // 默认的正整数
        Arrays.sort(nums);
            for (int i = 0; i < nums.length; i++) {
            if (nums[0] != 1 && nums[i] > num){
                break;
            }
            num++;
        }
        return num == 0 ? 1 : num;
    }

    public static void main(String[] args) {
        int[] arr = {1};
        System.out.println(firstMissingPositive(arr));
        Map<Integer,Integer> map = new HashMap();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {

        }
    }


    public List<Integer> findDuplicates(int[] nums) {

        Map<Integer,Integer> map = new HashMap();
        for(int i = 0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
                map.put(nums[i], map.get(nums[i]) + 1);
            }else{
                map.put(nums[i],1);
            }
        }
        List<Integer> list = new ArrayList(nums.length);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if(entry.getValue() == 2){
                list.add(entry.getKey());
            }
        }
        return list;
    }



}
