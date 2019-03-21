package com.foutin.thread.security;

import java.util.concurrent.CountDownLatch;

/**
 * 并发包下CountDownLatch的使用
 * @author xingkai.fan
 * @date 2019/2/19 13:18
 */
public class CountDownLatchDemo {


    public static void countDownLatch(){
        final CountDownLatch downLatch = new CountDownLatch(2);

        new Thread(() -> {
            System.out.println("线程"+Thread.currentThread().getName()+"正在执行...");
            try {
                Thread.sleep(2000);
                System.out.println("线程"+Thread.currentThread().getName()+"执行完毕");
                downLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            System.out.println("线程"+Thread.currentThread().getName()+"正在执行...");
            try {
                Thread.sleep(2000);
                System.out.println("线程"+Thread.currentThread().getName()+"执行完毕");
                downLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

        new Thread(() -> {
            try {
                System.out.println("线程"+Thread.currentThread().getName()+"正在等待前两个线程执行...");
                downLatch.await();
                System.out.println("线程"+Thread.currentThread().getName()+"执行完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();

    }

    public static void main(String[] args) {
        countDownLatch();
    }
}
