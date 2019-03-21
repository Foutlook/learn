package com.foutin.io.aio;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AIO服务器端
 * @author xingkai.fan
 * @date 2019/2/22 16:01
 */
public class AIOServer {
    //线程池
    private ExecutorService executorService;
    //线程组
    private AsynchronousChannelGroup threadGroup;
    //服务器通道
    public AsynchronousServerSocketChannel assc;

    public AIOServer(int port){
        try {
            //创建一个缓存池
            executorService = Executors.newCachedThreadPool();
            //创建线程组
            threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
            //创建服务器通道
            assc = AsynchronousServerSocketChannel.open(threadGroup);
            //进行绑定
            assc.bind(new InetSocketAddress(port));

            System.out.println("server start , port : " + port);
            //进行阻塞
            assc.accept(this, new ServerCompletionHandler());
            //一直阻塞 不让服务器停止
            Thread.sleep(Integer.MAX_VALUE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AIOServer(8765);
    }
}
