package com.foutin.io.netty.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 把message转正buf
 * @author xingkai.fan
 * @date 2019/2/26 13:25
 */
public class MessageHandler extends MessageToByteEncoder<User> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, User user, ByteBuf byteBuf) throws Exception {

        byteBuf.writeBytes(user.toString().getBytes());

    }
}
