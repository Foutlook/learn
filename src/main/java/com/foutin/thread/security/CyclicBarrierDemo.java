package com.foutin.thread.security;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 并发包下CyclicBarrier的使用
 *
 * @author xingkai.fan
 * @date 2019/2/19 14:05
 */
public class CyclicBarrierDemo {


    private static void cyclicBarrier() {
        final CyclicBarrier barrier = new CyclicBarrier(3);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(() -> {
            try {
                System.out.println("线程"+Thread.currentThread().getName() + "未准备");
                Thread.sleep(3000);
                System.out.println("线程" + Thread.currentThread().getName() + "准备完毕");
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() ->{
            try {
                System.out.println("线程"+Thread.currentThread().getName() + "正在准备");
                Thread.sleep(2000);
                System.out.println("线程" + Thread.currentThread().getName() + "准备完毕");
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        executorService.submit(() -> {
            try {
                System.out.println("线程" + Thread.currentThread().getName() + "准备完毕");
                barrier.await();    //await是等待其他线程都准备完毕后，一起往下执行。
                System.out.println("OK! GO!");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });


        executorService.shutdown();
    }


    public static void main(String[] args) {
        cyclicBarrier();

    }
}
