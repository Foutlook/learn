package com.foutin.thread.security;


/**
 * @author xingkai.fan
 * @date 2019/1/20 15:48
 */
public class VolatileDemo {

    private static volatile long _longVal = 0;

    private static class LoopVolatile implements Runnable {

        @Override
        public void run() {
            long val = 0;
            while (val < 1000000L) {
                _longVal++;
                val++;
            }
        }
    }

    private static class LoopVolatile2 implements Runnable {

        @Override
        public void run() {
            long val = 0;
            while (val < 1000000L) {
                _longVal++;
                val++;
            }
        }
    }

    private  void testVolatile(){

        /*ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 2L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2),Executors.defaultThreadFactory());
        threadPoolExecutor.submit(new LoopVolatile());
        threadPoolExecutor.submit(new LoopVolatile2());*/

        Thread t1 = new Thread(new LoopVolatile());
        t1.start();
        Thread t2 = new Thread(new LoopVolatile2());
        t2.start();

        while (t1.isAlive() || t2.isAlive()) {
        }

        System.out.println("final val is: " + _longVal);
    }


    public static void main(String[] args) {
        VolatileDemo volatileDemo = new VolatileDemo();
        volatileDemo.testVolatile();
    }
}
