package com.foutin.io.netty.google.protobuf.client;

import com.foutin.io.netty.client.Client;
import com.foutin.io.netty.client.ClientHandler;
import com.foutin.io.netty.google.protobuf.DataInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author xingkai.fan
 * @date 2019/2/27 10:04
 */
public class ProtoClient {

    private String host;
    private int port;

    public ProtoClient(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();

                    //解码器，通过Google Protocol Buffers序列化框架动态的切割接收到的ByteBuf
                    pipeline.addLast(new ProtobufVarint32FrameDecoder());
                    //将接收到的二进制文件解码成具体的实例，这边接收到的是服务端的ResponseBank对象实列
                    pipeline.addLast(new ProtobufDecoder(DataInfo.ResponseBank.getDefaultInstance()));
                    //Google Protocol Buffers编码器
                    pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                    //Google Protocol Buffers编码器
                    pipeline.addLast(new ProtobufEncoder());

                    pipeline.addLast(new ProtoClientHandler());
                }
            });

            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        ProtoClient client = new ProtoClient("127.0.0.1", 8888);
        try {
            client.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
