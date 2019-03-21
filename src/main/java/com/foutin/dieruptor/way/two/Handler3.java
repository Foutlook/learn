package com.foutin.dieruptor.way.two;


import com.foutin.dieruptor.way.one.Trade;
import com.lmax.disruptor.EventHandler;

public class Handler3 implements EventHandler<Trade> {
    @Override  
    public void onEvent(Trade event, long sequence,  boolean endOfBatch) throws Exception {  
    	System.out.println("handler3: name: " + event.getName() + " , price: " + event.getPrice() + ";  instance: " + event.toString());
    }  
}
