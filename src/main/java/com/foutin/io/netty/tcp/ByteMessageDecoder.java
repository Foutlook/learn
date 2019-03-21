package com.foutin.io.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 1.ByteToMessageDecoder是ChannelHandler的一个实现类，他可以在处理数据拆分的问题上变得很简单。
   2.每当有新数据接收的时候，ByteToMessageDecoder都会调用decode()方法来处理内部的那个累积缓冲。
   3.Decode()方法可以决定当累积缓冲里没有足够数据时可以往out对象里放任意数据。当有更多的数据被接收了ByteToMessageDecoder会再一次调用decode()方法。
   4.如果在decode()方法里增加了一个对象到out对象里，这意味着解码器解码消息成功。ByteToMessageDecoder将会丢弃在累积缓冲里已经被读过的数据。
     请记得你不需要对多条消息调用decode()，ByteToMessageDecoder会持续调用decode()直到不放任何数据到out里。
 * 解码
 * @author xingkai.fan
 * @date 2019/2/25 18:29
 */
public class ByteMessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < 4) {
            return; // (3)
        }
        list.add(byteBuf.readBytes(4)); // (4)
    }
}
