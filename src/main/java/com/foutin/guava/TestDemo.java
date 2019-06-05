package com.foutin.guava;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * @author xingkai.fan
 * @description
 * @date 2019/4/19 10:24
 */
public class TestDemo {

    public static void main(String[] args) {

        /*List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.removeIf("1"::equals);

        List<String> list2 = new ArrayList<>();
        list2.add("1");
        list2.add("2");

        for (String str : list2) {
            if ("1".equals(str)) {
                list2.remove(str);
                System.out.println("list2:" + list2.get(0));
            }
        }

        List<String> userNames = new ArrayList<String>() {{
            add("Hollis");
            add("hollis");
            add("HollisChuang");
            add("H");
        }};
        for (String userName : userNames) {
            if (userName.equals("Hollis")) {
                userNames.remove(userName);
            }}
        System.out.println(userNames);*/

        int i = 0;
        for (; i < 3; i++) {
            if (i == 1 ) {
                break;
            }
        }
        System.out.println(i);

    }

}
