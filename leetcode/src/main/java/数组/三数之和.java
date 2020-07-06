package 数组;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class 三数之和 {

    public static void main(String[] args) {
        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
    }


    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList();
        // 双指针前提是需要先排序
        Arrays.sort(nums);
        int len = nums.length;
        int sum = 0;
        for( int k = 0 ; k < len - 2 ; k ++){
            // 如果nums[k] 大于0,那么三数之和绝不可能等于0
            if(nums[k] > 0) break;

            if( k > 0 && nums[k] == nums[k - 1]) continue;

            int i = k + 1;
            int j = len - 1;
            while(i < j){
                sum = nums[k] + nums[i] + nums[j];
                if(sum == 0){
                    result.add(Arrays.asList(nums[k],nums[i],nums[j]));

                    while(i < j && nums[i] == nums[++i]);
                    while(i < j && nums[j] == nums[--j]);

                }
                else if(sum > 0) {j --;}
                else {i ++;}
            }
        }



        return result;

    }

}
