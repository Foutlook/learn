package com.foutin.io.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * NIO 客户端 , 服务器向客户端写数据和这个相反，客户端需要有一个Selector，监听服务器端
 * @author xingkai.fan
 * @date 2019/2/22 14:49
 */
public class NIOClient {

    private static void client() {
        //打开通道
        try (SocketChannel sc = SocketChannel.open()) {
            //创建连接的地址
            InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), 8888);

            //建立缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            //进行连接
            sc.connect(address);

            while (true) {
                //定义一个字节数组，然后使用系统录入功能：
                byte[] bytes = new byte[1024];
                System.in.read(bytes);

                //把数据放到缓冲区中
                buf.put(bytes);
                //对缓冲区进行复位
                buf.flip();
                //写出数据
                sc.write(buf);
                //清空缓冲区数据
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        client();
    }
}
