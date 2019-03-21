package com.foutin.io.netty.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 1. ServerHandler 继承自 ChannelHandlerAdapter，这个类实现了ChannelHandler接口，ChannelHandler提供了许多事件处理的接口方法，
 * 然后你可以覆盖这些方法。现在仅仅只需要继承ChannelHandlerAdapter类而不是你自己去实现接口方法。
 * 2. 这里我们覆盖了chanelRead()事件处理方法。每当从客户端收到新的数据时，这个方法会在收到消息时被调用，这个例子中，收到的消息的类型是ByteBuf
 * 3. ByteBuf是一个引用计数对象，这个对象必须显示地调用release()方法来释放。请记住处理器的职责是释放所有传递到处理器的引用计数对象。
 * 通常，channelRead()方法的实现就像下面的这段代码：
 * public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            // Do something with msg
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
 * @author xingkai.fan
 * @date 2019/2/25 11:10
 */

public class ServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        try {
//            ((ByteBuf) msg).release();
            //do something
            /*ByteBuf buf = (ByteBuf) msg;

            while (buf.isReadable()) { // (1)
                System.out.print((char) buf.readByte());
                System.out.flush();
            }*/

        //使用解码方式1
        String str = (String) msg;
        System.out.println(str);

        //返回客户端数据, 如果在这里write，buf应用对象系统会自动清理
              //返回值通过分隔符
//            ChannelFuture future = ctx.writeAndFlush(Unpooled.copiedBuffer(("kaixingfan"+System.getProperty("line.separator")).getBytes()));

        //通过长度限制
        ctx.writeAndFlush(Unpooled.copiedBuffer("fanxingkai".getBytes()));

            //添加一个监听，消息发送就会立即关闭连接，netty通过这种方式就可以实现长连接和短连接
//            future.addListener(ChannelFutureListener.CLOSE);

//        } finally {
//            //清理引用对象
//            ReferenceCountUtil.release(msg);
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        ctx.close();
    }
}
