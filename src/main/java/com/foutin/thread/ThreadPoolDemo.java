package com.foutin.thread;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author xingkai.fan
 * @date 2019/3/28 11:33
 */
public class ThreadPoolDemo {

    public static void demo(){
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 4, 1000, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5), r -> {
            Thread t = new Thread(r);
            t.setName("T " + t.getId() + "_" +System.currentTimeMillis());
            t.setDaemon(true);
            System.out.println("Create a Thread Name is : "+t.getName());
            return t;
        });

        MyTask myTask = new MyTask();
        for (int i=0;i<8;i++){
            poolExecutor.submit(myTask);
        }
        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        int i = 2345/1000;
        System.out.println(i);
//        demo();

        HashSet<String> set = new HashSet<>();
        List<String> list = new ArrayList<>();
        list.add("fanxingkai");
        list.forEach(s -> set.add(s.toUpperCase()));
        set.forEach(System.out::println);
    }

    public static class MyTask implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " coming...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
