package com.foutin.io.netty.marshalling.server;


import com.foutin.io.netty.marshalling.Req;
import com.foutin.io.netty.marshalling.Resp;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author xingkai.fan
 * @date 2019/2/27 9:50
 */
public class MarshallingServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Req req = (Req) msg;
        System.out.println(req.toString());

        Resp resp = new Resp();
        resp.setId(req.getId());
        resp.setName("kai");
        resp.setResponseMessage("dashabi");
        ctx.writeAndFlush(resp);

    }
}
