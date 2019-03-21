package com.foutin.io.netty.google.protobuf.server;

import com.foutin.io.netty.google.protobuf.DataInfo;
import com.foutin.io.netty.server.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author xingkai.fan
 * @date 2019/2/27 9:38
 */
public class ProtoServer {

    private int port;
    private ProtoServer(int port){
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
                            //解码器，通过Google Protocol Buffers序列化框架动态的切割接收到的ByteBuf
                            pipeline.addLast(new ProtobufVarint32FrameDecoder());
                            //服务器端接收的是客户端RequestUser对象，所以这边将接收对象进行解码生产实列
                            pipeline.addLast(new ProtobufDecoder(DataInfo.RequestUser.getDefaultInstance()));
                            //Google Protocol Buffers编码器
                            pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                            //Google Protocol Buffers编码器
                            pipeline.addLast(new ProtobufEncoder());
                            pipeline.addLast(new ProtoServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
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
            new ProtoServer(8888).run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
