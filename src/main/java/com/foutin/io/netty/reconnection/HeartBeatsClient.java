package com.foutin.io.netty.reconnection;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;

import java.util.concurrent.TimeUnit;

/**
 * @author xingkai.fan
 * @date 2019/2/28 10:22
 */
public class HeartBeatsClient {

    protected final HashedWheelTimer timer = new HashedWheelTimer();

    private Bootstrap boot;

    private final ConnectorIdleStateTrigger idleStateTrigger = new ConnectorIdleStateTrigger();

    public void connect(int port, String host) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();

        boot = new Bootstrap();
        boot.group(group).channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.SO_KEEPALIVE, true);

        final ConnectionWatchdog watchdog = new ConnectionWatchdog(boot, timer, port,host, true) {

            @Override
            public ChannelHandler[] handlers() {
                return new ChannelHandler[] {
                        this,
                        new IdleStateHandler(0, 9, 0, TimeUnit.SECONDS),
                        idleStateTrigger,
                        new StringDecoder(),
                        new StringEncoder(),
                        new HeartBeatClientHandler()
                };
            }
        };

        ChannelFuture future;
        //进行连接
        try {
            synchronized (boot) {
                boot.handler(new ChannelInitializer<SocketChannel>() {

                    //初始化channel
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(watchdog.handlers());
                    }
                });

                future = boot.connect(host,port);
            }

            // 以下代码在synchronized同步块外面是安全的
            future.sync();

            future.channel().closeFuture().sync();

        } catch (Throwable t) {
            throw new Exception("connects to  fails", t);
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        new HeartBeatsClient().connect(port, "127.0.0.1");
    }
}
