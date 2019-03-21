package com.foutin.io.netty.pojo.client;

import com.foutin.io.netty.pojo.User;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

import java.util.UUID;

/**
 * @author xingkai.fan
 * @date 2019/2/25 13:54
 */
public class ClientHandler extends ChannelHandlerAdapter {

    private byte[] req;

    //channelActive()方法将会在连接被建立并且准备进行通信时被调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //写数据,写给服务器
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName("fanxingkai");
        ctx.writeAndFlush(user);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
//            ((ByteBuf) msg).release();
            //do something
            ByteBuf buf = (ByteBuf) msg;

            while (buf.isReadable()) { // (1)
                System.out.print((char) buf.readByte());
                System.out.flush();
            }

        } finally {
            //清理引用对象
            ReferenceCountUtil.release(msg);
        }
    }
}
