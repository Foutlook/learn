package com.foutin.dieruptor.simple.use.multi;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;

/**
 * 使用disruptor 实现多生产者多消费者
 * @author xingkai.fan
 * @date 2019/2/21 16:24
 */
public class MoreProducersAndCustomer {
    public static void producerAndCustomerDemo() throws InterruptedException {
        int size = 1024;
        Disruptor<Transaction> disruptor = new Disruptor<>(
                Transaction::new, size,
                (ThreadFactory) Thread::new,
                ProducerType.MULTI,
                new YieldingWaitStrategy());

        CustomerEventHandler[] handlers = new CustomerEventHandler[3];
        for (int i=0;i<3;i++){
            CustomerEventHandler handler = new CustomerEventHandler(i);
            handlers[i] = handler;
            //添加事件处理
            disruptor.handleEventsWith(handler);
        }

        disruptor.start();

        RingBuffer<Transaction> ringBuffer = disruptor.getRingBuffer();

        final CountDownLatch latch = new CountDownLatch(1);
        int m = 0;
        for (int i =0;i<10;i++){
            /*final TransactionProducer producer = new TransactionProducer(ringBuffer);*/
            TransactionProducerWithTranslator producer = new TransactionProducerWithTranslator(ringBuffer);
            for (int j=0;j<10;j++){
                int fj = j;
                int fi = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            latch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        producer.onData("fan"+ fj, (double) (fj + fi));
                    }
                }).start();
                m++;
            }
        }
        System.out.println(m+"=============");

        Thread.sleep(2000);
        System.out.println("---------------开始生产-----------------");
        latch.countDown();
        Thread.sleep(5000);
        System.out.println("sum="+handlers[0].getCount());

    }

    public static void main(String[] args) throws InterruptedException {
        producerAndCustomerDemo();
    }

}
