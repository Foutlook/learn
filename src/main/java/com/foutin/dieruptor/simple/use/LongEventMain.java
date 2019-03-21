package com.foutin.dieruptor.simple.use;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadFactory;

/**
 * 以后直接用这个模式就可以
 * @author xingkai.fan
 * @date 2019/2/21 10:59
 */
public class LongEventMain {

    /**
     * 第一种方式，传统的不适用Lambda表达式
     *
     * @throws InterruptedException
     */
    private static void longEventDemo1() throws InterruptedException {
        // Executor that will be used to construct new threads for consumers
//        Executor executor = Executors.newCachedThreadPool();
        // The factory for the event
        LongEventFactory factory = new LongEventFactory();
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;
        // Construct the Disruptor
//        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, bufferSize, executor);
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, bufferSize, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
        // Connect the handler
        disruptor.handleEventsWith(new LongEventHandler());
        // Start the Disruptor, starts all threads running
        disruptor.start();
        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        LongEventProducer producer = new LongEventProducer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            producer.onData(bb);
            Thread.sleep(1000);
        }
    }


    /**
     * 用lambda表达式来注册EventHandler和EventProductor
     *
     * @throws InterruptedException
     */
    private static void longEventDemo2() throws InterruptedException {
        // Executor that will be used to construct new threads for consumers
//        Executor executor = Executors.newCachedThreadPool();
        // Specify the size of the ring buffer, must be power of 2.
        // Construct the Disruptor
        int bufferSize = 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(LongEvent::new, bufferSize, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });

        //创建2,添加两个参数ProducerType和YieldingWaitStrategy
        /*Disruptor<LongEvent> eventDisruptor = new Disruptor<>(LongEvent::new, bufferSize, (ThreadFactory) Thread::new,
                ProducerType.SINGLE, new YieldingWaitStrategy());*/

        // 可以使用lambda来注册一个EventHandler
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> System.out.println("Event: " + event.getValue()));
        // Start the Disruptor, starts all threads running
        disruptor.start();
        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            //可以简化成Lambda表达式，这里为了清楚要做的事情，不再简化
            ringBuffer.publishEvent(new EventTranslatorOneArg<LongEvent, ByteBuffer>() {

                @Override
                public void translateTo(LongEvent longEvent, long sequence, ByteBuffer buffer) {
                    longEvent.setValue(buffer.getLong(0));
                }
            },bb);

            //或者使用下面的方式，因为bb上面已经声明了，不需要再传递到里面了
            /*ringBuffer.publishEvent(new EventTranslator<LongEvent>() {
                @Override
                public void translateTo(LongEvent longEvent, long sequence) {
                    longEvent.setValue(bb.getLong(0));
                }
            });*/

            Thread.sleep(1000);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //第一种
//        longEventDemo1();
        //第二种
        longEventDemo2();
    }
}
