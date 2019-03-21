package com.foutin.io.netty.marshalling.server;

import com.foutin.io.netty.marshalling.MarshallingCodeCFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author xingkai.fan
 * @date 2019/2/27 9:38
 */
public class MarshallingServer {

    private int port;
    private MarshallingServer(int port){
        this.port = port;
    }

    private void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            pipeline.addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                            pipeline.addLast(new MarshallingServerHandler());
                        }
                    })
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new LoggingHandler(LogLevel.INFO))    //设置日志
                    .childOption(ChannelOption.SO_KEEPALIVE, true);


            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new MarshallingServer(8989).run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
