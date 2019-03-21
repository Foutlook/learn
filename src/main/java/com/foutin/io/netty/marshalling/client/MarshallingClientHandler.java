package com.foutin.io.netty.marshalling.client;

import com.foutin.io.netty.marshalling.Req;
import com.foutin.io.netty.marshalling.Resp;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * @author xingkai.fan
 * @date 2019/2/27 10:08
 */
public class MarshallingClientHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 1; i < 6; i++) {
            Req req = new Req();
            req.setId(String.valueOf(i));
            req.setName("fan"+i);
            req.setRequestMessage("hualalala");
            req.setAttachment("fanxingkai".getBytes());
            ctx.writeAndFlush(req);
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            Resp resp = (Resp) msg;
            System.out.println(resp.toString());
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
