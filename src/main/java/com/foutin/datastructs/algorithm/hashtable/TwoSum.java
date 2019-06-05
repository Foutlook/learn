package com.foutin.datastructs.algorithm.hashtable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author xingkai.fan
 * @description 和散列（哈希）表有关的算法
 * @date 2019/5/31 14:04
 * <p>
 * 题目描述：给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 * <p>
 * 例如：
 * 给定 nums = [2, 7, 11, 15], target = 9
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 * <p>
 * 解析：使用散列表来解决该问题。
 * 首先设置一个 map 容器 record 用来记录元素的值与索引，然后遍历数组 nums 。
 * - 每次遍历时使用临时变量 complement 用来保存目标值与当前值的差值
 * - 在此次遍历中查找 record ，查看是否有与 complement 一致的值，如果查找成功则返回查找值的索引值与当前变量的值i
 * - 如果未找到，则在 record 保存该元素与索引值 i
 */
public class TwoSum {

    private static int[] sum(int[] nums, int target) {
        int complement;
        Map<Integer, Integer> record = new HashMap<>(16);
        for (int i = 0; i < nums.length; i++) {
            complement = target - nums[i];
            int finalComplement = complement;
            if (record.size() > 0) {
                Optional<Integer> value = record.keySet().stream().filter(key -> key.equals(finalComplement)).findFirst();
                if (value.isPresent()) {
                    int index = record.get(value.get());
                    return new int[]{index, i};
                }
            }
            record.put(nums[i], i);
        }
        return new int[2];
    }

    public static void main(String[] args) {
        int[] nums = {2, 12, 7, 11, 15};
        int target = 9;
        int[] sum = sum(nums, target);
        System.out.println(Arrays.toString(sum));
    }
}
