package com.foutin.io.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author xingkai.fan
 * @date 2019/2/22 13:43
 */
public class NIOServer implements Runnable {
    //1 多路复用器（管理所有的通道）
    private Selector s;
    //2 建立缓冲区
    private ByteBuffer readBuf = ByteBuffer.allocate(1024);

    @Override
    public void run() {
        try (Selector selector = Selector.open();
             // 创建 Selector 和 Channel
             ServerSocketChannel serverSocket = ServerSocketChannel.open()) {

            serverSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(), 8888));
            s = selector;
            serverSocket.configureBlocking(false);
            // 注册到 Selector，并说明关注点
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();// 阻塞等待就绪的 Channel，这是关键点之一
                System.out.println("有客户端访问...");
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();

                    // 生产系统中一般会额外进行就绪状态检查
                    //阻塞状态
                    if (key.isAcceptable()) {
                        accept(key);
                    }
                    //可读状态
                    if (key.isReadable()) {
                        sayHelloWorld(key);
                    }

                    iter.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //阻塞状态客户端需要首先注册到Selector中
    private void accept(SelectionKey key) {
        try {
            //1 获取服务通道
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            //2 执行阻塞方法
            SocketChannel sc = ssc.accept();
            //3 设置阻塞模式
            sc.configureBlocking(false);
            //4 注册到多路复用器上，并设置读取标识
            sc.register(s, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //接收客户端发来的数据
    private void sayHelloWorld(SelectionKey key) {
        try {
            //1 清空缓冲区旧的数据
            readBuf.clear();
            //2 获取之前注册的socket通道对象
            SocketChannel sc = (SocketChannel) key.channel();
            //3 读取数据,把sc中的数据保存到readBuf中
            int count = sc.read(readBuf);
            //4 如果没有数据
            if (count == -1) {
                key.channel().close();
                key.cancel();
                return;
            }
            //5 有数据则进行读取 读取之前需要进行复位方法(把position 和limit进行复位)
            readBuf.flip();
            //6 根据缓冲区的数据长度创建相应大小的byte数组，接收缓冲区的数据
            byte[] bytes = new byte[readBuf.remaining()];
            //7 接收缓冲区数据
            readBuf.get(bytes);
            //8 打印结果
            String body = new String(bytes).trim();
            System.out.println("Server : " + body);

            // 9..可以写回给客户端数据

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new NIOServer()).start();
    }

}
