package com.foutin.concurrency.collections;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @author xingkai.fan
 * @date 2019/1/22 10:28
 */
public class CopyOnWriteDemo {

    public void copyOnWriteTest(){
        CopyOnWriteArrayList<String> strings = new CopyOnWriteArrayList<String>();
        ConcurrentLinkedQueue<String> strings1 = new ConcurrentLinkedQueue<String>();


        CountDownLatch countDownLatch = new CountDownLatch(2);


    }

    public static void main(String[] args) {
        String property = System.getProperty("user.dir").replaceAll("\\\\","/");
        System.out.println("System.getProperty="+property);


        String path = "D:/idea-workspace/privately/fan/TscmTargetSubtargetService.java";
        File file = new File(path);
        try {
            boolean newFile = file.createNewFile();
            System.out.println(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
