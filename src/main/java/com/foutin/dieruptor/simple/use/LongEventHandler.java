package com.foutin.dieruptor.simple.use;

import com.lmax.disruptor.EventHandler;

/**
 * 事件消费者
 * @author xingkai.fan
 * @date 2019/2/21 10:22
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println(longEvent.getValue());
    }
}
