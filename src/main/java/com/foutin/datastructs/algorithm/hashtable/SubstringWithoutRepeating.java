package com.foutin.datastructs.algorithm.hashtable;

import javax.validation.constraints.Max;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xingkai.fan
 * @description 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * @date 2019/5/31 15:23
 * <p>
 * 解析：
 * - 建立一个 HashMap ，建立每个字符和其最后出现位置之间的映射，然后再定义两个变量 res 和 left ，其中 res 用来记录最长无重复子串的长度，
 * left 指向该无重复子串左边的起始位置的前一个，一开始由于是前一个，所以在初始化时就是 -1。
 * <p>
 * - 接下来遍历整个字符串，对于每一个遍历到的字符，如果该字符已经在 HashMap 中存在了，并且如果其映射值大于 left 的话，那么更新
 * left 为当前映射值，然后映射值更新为当前坐标 i，这样保证了 left 始终为当前边界的前一个位置，然后计算窗口长度的时候，直接用
 * i-left 即可，用来更新结果 res 。
 */
public class SubstringWithoutRepeating {

    public static int subString(String subString) {
        Map<Character, Integer> record = new HashMap<>(16);
        int res = 0;
        int left = -1;
        char[] chars = subString.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (record.get(chars[i]) != null && record.get(chars[i]) > left) {
                left = record.get(chars[i]);
            }
            record.put(chars[i], i);
            res = Math.max(res, i - left);
        }
        return res;
    }

    public static void main(String[] args) {
        String str = "substring";
        int i = subString(str);
        System.out.println(i);
    }
}
