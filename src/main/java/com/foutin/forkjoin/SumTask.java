package com.foutin.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author xingkai.fan
 * @date 2019/2/26 14:40
 */
public class SumTask extends RecursiveTask<Long> {

    private static final int THRESHOLD = 100;
    private long[] array;
    private int start;
    private int end;

    private SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }


    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            // 如果任务足够小,直接计算:
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.println(String.format("compute %d~%d = %d", start, end, sum));
            return sum;
        }
        // 任务太大,一分为二:
        int middle = (end + start) / 2;
        System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, middle, middle, end));
        SumTask subtask1 = new SumTask(this.array, start, middle);
        SumTask subtask2 = new SumTask(this.array, middle, end);
        invokeAll(subtask1, subtask2);
        Long subresult1 = subtask1.join();
        Long subresult2 = subtask2.join();
        Long result = subresult1 + subresult2;
        System.out.println("result = " + subresult1 + " + " + subresult2 + " ==> " + result);
        return result;
    }


    private static void fillRandom(long[] array){
        for (int i=0;i<400;i++){
            array[i] = i+1;
        }
    }

    public static void main(String[] args) {
        // 创建随机数组成的数组:
        long[] array = new long[400];
        fillRandom(array);
        // fork/join task:
        ForkJoinPool fjp = new ForkJoinPool(4); // 最大并发数4
        ForkJoinTask<Long> task = new SumTask(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        //fjp.invoke(task)来提交一个Fork/Join任务并发执行，然后获得异步执行的结果。
        Long result = fjp.invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
    }
}
