package com.foutin.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author xingkai.fan
 * @date 2019/3/1 14:07
 */
public class ForkJoinTaskDemo extends RecursiveTask<Long> {

    private static final long MAX = 1000000000L;
    private static final long THRESHOLD = 1000L;
    private long start;
    private long end;


    public ForkJoinTaskDemo(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if (end - start <= THRESHOLD) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long mid = (start + end) / 2;
            ForkJoinTaskDemo task1 = new ForkJoinTaskDemo(start, mid);
            ForkJoinTaskDemo task2 = new ForkJoinTaskDemo(mid + 1, end);
            invokeAll(task1, task2);
            return task1.join() + task2.join();
        }
    }


    private static void test() {
        System.out.println("test");
        long start = System.currentTimeMillis();
        Long sum = 0L;
        for (long i = 0L; i <= MAX; i++) {
            sum += i;
        }
        System.out.println(sum);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    private static void testForkJoin() {
        System.out.println("testForkJoin");
        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Long sum = forkJoinPool.invoke(new ForkJoinTaskDemo(1, MAX));
        System.out.println(sum);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    public static void main(String[] args) {
        test();
        System.out.println("--------------------");
        testForkJoin();
    }

}
