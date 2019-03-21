package com.foutin.dieruptor.simple.use.multi;

import com.lmax.disruptor.EventHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消费者
 * @author xingkai.fan
 * @date 2019/2/21 16:41
 */
public class CustomerEventHandler implements EventHandler<Transaction> {

    private int flagId;
    private AtomicInteger count = new AtomicInteger(0);

    public CustomerEventHandler(int flagId){
        this.flagId = flagId;
    }

    public int getFlagId() {
        return flagId;
    }

    public int getCount() {
        return count.get();
    }

    @Override
    public void onEvent(Transaction transaction, long l, boolean b) throws Exception {
        System.out.println("Customer["+getFlagId()+"] handler: name="+ transaction.getName()+" price="+transaction.getPrice());
        count.incrementAndGet();
    }
}
