package com.foutin.io.netty.marshalling.client;

import com.foutin.io.netty.client.Client;
import com.foutin.io.netty.client.ClientHandler;
import com.foutin.io.netty.google.protobuf.DataInfo;
import com.foutin.io.netty.marshalling.MarshallingCodeCFactory;
import com.foutin.io.netty.marshalling.Req;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * @author xingkai.fan
 * @date 2019/2/27 10:04
 */
public class MarshallingClient {

    private String host;
    private int port;

    public MarshallingClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            //设置日志
            b.handler(new LoggingHandler(LogLevel.INFO));
            b.option(ChannelOption.TCP_NODELAY, true);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(MarshallingCodeCFactory.buildMarshallingEncoder());//编码器M
                    pipeline.addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                    //超时handler（当服务器端与客户端在指定时间以上没有任何进行通信，则会关闭响应的通道，主要为减小服务端资源占用）
                    pipeline.addLast(new ReadTimeoutHandler(10));
                    pipeline.addLast(new MarshallingClientHandler());
                }
            });

            ChannelFuture f = b.connect(host, port).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        try {
            new MarshallingClient("127.0.0.1", 8989).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
