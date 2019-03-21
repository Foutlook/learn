package com.foutin.dieruptor.simple.use;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * 事件源---生产者---通过Translator来赋值
 *
 * Disruptor 3.0提供了lambda式的API。这样可以把一些复杂的操作放在Ring Buffer，
 * 所以在Disruptor3.0以后的版本最好使用Event Publisher或者Event Translator来发布事件.
 * Disruptor提供了不同的接口(EventTranslator, EventTranslatorOneArg, EventTranslatorTwoArg, 等等)去产生一个Translator对象。
 * 很明显，Translator中方法的参数是通过RingBuffer来传递的。
 *
 * @author xingkai.fan
 * @date 2019/2/21 10:50
 */
public class LongEventProducerWithTranslator {
    //一个translator可以看做一个事件初始化器，publicEvent方法会调用它
    //填充Event
    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR =
            new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
                @Override
                public void translateTo(LongEvent event, long sequence, ByteBuffer bb) {
                    event.setValue(bb.getLong(0));
                }
            };

    private final RingBuffer<LongEvent> ringBuffer;
    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer bb) {
        //放入ringBuffer中，不用自己手动的去处理参数
        ringBuffer.publishEvent(TRANSLATOR, bb);
    }
}
