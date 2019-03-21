package com.foutin.io.netty.http.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

/**
 * @author xingkai.fan
 * @date 2019/2/28 15:00
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    public final SslContext sslCtx;

    public HttpServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        ChannelPipeline pipeline = sc.pipeline();
        if (sslCtx != null) {
            pipeline.addLast(sslCtx.newHandler(sc.alloc()));
        }
        pipeline.addLast(new HttpServerCodec());//添加httpServer
        pipeline.addLast(new HttpServerHandler());
    }

}
