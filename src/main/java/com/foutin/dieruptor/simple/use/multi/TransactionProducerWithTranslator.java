package com.foutin.dieruptor.simple.use.multi;

import com.foutin.dieruptor.simple.use.LongEvent;
import com.lmax.disruptor.EventTranslatorTwoArg;
import com.lmax.disruptor.RingBuffer;


/**
 * @author xingkai.fan
 * @date 2019/2/22 9:57
 */
public class TransactionProducerWithTranslator {

    private final EventTranslatorTwoArg<Transaction,String,Double> translatorTwoArg = (transaction, sequence, name, aDouble) -> {
        transaction.setName(name);
        transaction.setPrice(aDouble);
    };

    private final RingBuffer<Transaction> ringBuffer;
    public TransactionProducerWithTranslator(RingBuffer<Transaction> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }
    public void onData(String name,Double price) {
        //放入ringBuffer中，不用自己手动的去处理参数
        ringBuffer.publishEvent(translatorTwoArg,name,price);
    }

}
