package com.foutin.thread.security;

import java.util.concurrent.*;

/**
 * @author xingkai.fan
 * @date 2019/2/19 15:56
 */
public class FutureTaskDemo {

    private static ExecutorService executorService;

    private static void futureTask() {
        executorService = Executors.newFixedThreadPool(3);
        CountDownLatch downLatch = new CountDownLatch(1);

        FutureTask<String> taskCall = new FutureTask<>(() -> {
            System.out.println("调用Callable的call方法");
            Thread.sleep(3000);
            downLatch.countDown();
            return "我爱你";
        });

        String result = "滚";
        FutureTask<String> taskRun = new FutureTask<>(() -> {
            try {
                downLatch.await();
                System.out.println("调用Runnable的run方法");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, result);


        executorService.submit(taskCall);
        executorService.submit(taskRun);

        try {
            System.out.println(taskCall.get());
            System.out.println(taskRun.get());
//            executorService.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        futureTask();

        System.out.println("============================");

        Future<?> future = executorService.submit(() -> System.out.println("直接使用Runnable创建一个线程"));
        try {
            System.out.println(future.get()==null);
            executorService.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
