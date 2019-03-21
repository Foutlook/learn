package com.foutin.io.netty.google.protobuf.client;

import com.foutin.io.netty.google.protobuf.DataInfo;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * @author xingkai.fan
 * @date 2019/2/27 10:08
 */
public class ProtoClientHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        DataInfo.RequestUser user = DataInfo.RequestUser.newBuilder()
                .setUserName("xingkai.fan").setAge(25).setPassword("123456").build();

        ctx.writeAndFlush(user);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            DataInfo.ResponseBank bank = (DataInfo.ResponseBank) msg;
            System.out.println(bank.getBankName() + "--" + bank.getBankNo() + "--" + bank.getMoney());
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
