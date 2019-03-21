package com.foutin.dieruptor.simple.use;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * 事件源---生产者---程序员需要在读取到数据的时候自己触发事件并发布
 * @author xingkai.fan
 * @date 2019/2/21 10:28
 */
public class LongEventProducer {
    private final RingBuffer<LongEvent> ringBuffer;
    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * onData用来发布事件，每调用一次就发布一次事件事件
     * 它的参数会通过事件传递给消费者
     *
     * @param bb
     */public void onData(ByteBuffer bb) {
        //可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
        long sequence = ringBuffer.next();
        try {
            //用上面的索引取出一个空的事件用于填充
            // for the sequence
            LongEvent event = ringBuffer.get(sequence);
            event.setValue(bb.getLong(0));
        } finally {
            //发布事件
            ringBuffer.publish(sequence);
        }
    }
}
