package com.foutin.io.netty.pojo.client;

import com.foutin.io.netty.pojo.MessageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 1.BootStrap和ServerBootstrap类似,不过他是对非服务端的channel而言，比如客户端或者无连接传输模式的channel。
 * 2.如果你只指定了一个EventLoopGroup，那他就会即作为一个‘boss’线程，也会作为一个‘workder’线程，尽管客户端不需要使用到‘boss’线程。
 * 3.代替NioServerSocketChannel的是NioSocketChannel,这个类在客户端channel被创建时使用。
 * 4.不像在使用ServerBootstrap时需要用childOption()方法，因为客户端的SocketChannel没有父channel的概念。
 * 5.我们用connect()方法代替了bind()方法。
 *
 * @author xingkai.fan
 * @date 2019/2/25 13:50
 */
public class Client {
    private String host;
    private int port;

    public Client(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    //ch.pipeline().addLast(new ByteMessageDecoder(),new ClientHandler());
                    //方法1：LineBasedFrameDecoder 基于换行符解决。StringDecoder 可以把buf解码成字符串
//                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024),new StringDecoder(),new ClientHandler());

                    //方法2：DelimiterBasedFrameDecoder可基于分隔符解决。
                    //方法3：FixedLengthFrameDecoder可指定长度解决。
//                    ch.pipeline().addLast(new FixedLengthFrameDecoder(11),new StringDecoder(),new ClientHandler());
                    ch.pipeline().addLast(new MessageHandler(),new ClientHandler());
                }
            });
            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 8888);
        try {
            client.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
