package com.foutin.dieruptor.simple.use.multi;

import com.lmax.disruptor.RingBuffer;


/**
 * 生产者
 * @author xingkai.fan
 * @date 2019/2/21 17:07
 */
public class TransactionProducer {

    private final RingBuffer<Transaction> ringBuffer;
    public TransactionProducer(RingBuffer<Transaction> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(String name,double price) {
        long sequence = ringBuffer.next();
        Transaction transaction = ringBuffer.get(sequence);
        transaction.setName(name);
        transaction.setPrice(price);
        //放入ringBuffer中，不用自己手动的去处理参数
        ringBuffer.publish(sequence);
    }
}
