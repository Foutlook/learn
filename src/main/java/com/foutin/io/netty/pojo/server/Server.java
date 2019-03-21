package com.foutin.io.netty.pojo.server;

import com.foutin.io.netty.pojo.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author xingkai.fan
 * @date 2019/2/25 11:44
 */
public class Server {

    private int port;
    public Server(int port){
        this.port = port;
    }

    public void run() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //不使用TCP拆包
                            //ch.pipeline().addLast(new ServerHandler());
                            //TCP拆包
                            //方法1：LineBasedFrameDecoder 基于换行符解决。StringDecoder 可以把buf解码成字符串
//                            ch.pipeline().addLast(new LineBasedFrameDecoder(24),new StringDecoder(),new ServerHandler());

                            //方法2：DelimiterBasedFrameDecoder可基于分隔符解决。
                            //方法3：FixedLengthFrameDecoder可指定长度解决。
//                            ch.pipeline().addLast(new FixedLengthFrameDecoder(11),new StringDecoder(),new ServerHandler());

                            ch.pipeline().addLast(new MessageHandler(),new ServerHandler());
                        }
                    })
                    /*
                     * 设置tcp缓冲区
                     * 服务器端TCP内核模块有两个队列，暂时我们称之为A和B吧，客户端向服务器端connec的时候，会发送带有SYN标志的包（第一次握手），
                     * 服务端收到客户端发来的SYN时，向客户端发送SYN ACK确认（第二次握手），此时TCP内核模块把客户端连接加入到A队列中，
                     * 然后服务器收到客户端发来的ACK时（第三次握手），TCP内核模块把客户端连接从A队列移到B队列，连接完成，应用程序的accept会返回，
                     * 也就是说accept从B队列中取出完成三次握手完成的链接。
                     *
                     * A队列和B队列长度之和是backlog，当之和大于backlog时，新连接会被tcp拒绝。
                     */
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        Server server = new Server(8888);
        try {
            server.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
