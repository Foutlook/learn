package com.foutin.io.netty.http.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * @author xingkai.fan
 * @date 2019/2/28 15:00
 */
public class HttpServer {
    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8843" : "8080"));

    public static void main(String[] args) throws Exception {
        // 配置ssl
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
        } else {
            sslCtx = null;
        }
        // 配置服务
        EventLoopGroup bGroup = new NioEventLoopGroup(1);
        EventLoopGroup wGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bGroup,wGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new HttpServerInitializer(sslCtx));

            Channel channel = b.bind(PORT).sync().channel();

            System.err.println("打开浏览器，输入链接 " + (SSL ? "https" : "http") + "://127.0.0.1:" + PORT + '/');

            channel.closeFuture().sync();
        } finally {
            bGroup.shutdownGracefully();
            wGroup.shutdownGracefully();
        }
    }
}
