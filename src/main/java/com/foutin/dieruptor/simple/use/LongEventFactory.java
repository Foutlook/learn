package com.foutin.dieruptor.simple.use;

import com.lmax.disruptor.EventFactory;

/**
 * 事件工厂--实例化Event对象
 * @author xingkai.fan
 * @date 2019/2/21 10:01
 */
public class LongEventFactory implements EventFactory {
    @Override
    public Object newInstance() {
        return new LongEvent();
    }
}
