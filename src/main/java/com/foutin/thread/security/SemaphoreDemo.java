package com.foutin.thread.security;

import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

/**
 * 并发包下Semaphore的使用
 * @author xingkai.fan
 * @date 2019/2/19 14:50
 */
public class SemaphoreDemo {

    /**
     * 8个人，5台机器，一个人使用完后另一个人才能够使用
     */
    private static void semaphore(){
        int n = 8;
        Semaphore semaphore = new Semaphore(5);
        for (int i = 1; i <= n; i++) {
            new Thread( new Worker(i,semaphore)).start();
        }

    }


    static class Worker implements Runnable{

        private Semaphore semaphore;
        private int num;

        public Worker(int num,Semaphore semaphore){
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("第"+num+"个人抢到了一台机器");
                Thread.sleep(3000);
                System.out.println("啊 工作完成，工人"+num+"关闭了机器");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        semaphore();

    }
}
