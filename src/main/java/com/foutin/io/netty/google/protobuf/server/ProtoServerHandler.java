package com.foutin.io.netty.google.protobuf.server;

import com.foutin.io.netty.google.protobuf.DataInfo;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author xingkai.fan
 * @date 2019/2/27 9:50
 */
public class ProtoServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DataInfo.RequestUser requestUser = (DataInfo.RequestUser) msg;
        System.out.println(requestUser.getUserName()+"--"+requestUser.getAge()+"--"+requestUser.getPassword());

        DataInfo.ResponseBank bank = DataInfo.ResponseBank.newBuilder().setBankName("中国工商银行")
                .setBankNo("6222222200000000000").setMoney(560000.23).build();
        ctx.writeAndFlush(bank);
    }
}
