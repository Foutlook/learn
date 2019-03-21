package com.foutin.io.netty.marshalling;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * @author xingkai.fan
 * @date 2019/2/27 11:49
 */
public class MarshallingCodeCFactory {

    /**
     * 创建Jboss Marshalling解码器MarshallingDecoder
     * @return
     */
    public static MarshallingDecoder buildMarshallingDecoder(){
        //首先通过Marshalling工具类的方法获取Marshalling实例对象 参数serial标识创建的是java序列化工厂对象
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        //创建MarshallingConfiguration对象，配置版本号为5
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        //根据MarshallerFactory和configuration创建provide
        UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        //构建netty的marshallingDecoder，两个参数分别为provider和单个消息序列化后的最大长度
        return new MarshallingDecoder(provider,1024);
    }

    /**
     * 创建Jboss Marshalling编码器MarshallingEncoder
     * @return
     */
    public static MarshallingEncoder buildMarshallingEncoder(){
        //首先通过Marshalling工具类的方法获取Marshalling实例对象 参数serial标识创建的是java序列化工厂对象
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        //创建MarshallingConfiguration对象，配置版本号为5
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        //根据MarshallerFactory和configuration创建provide
        MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        return new MarshallingEncoder(provider);

    }
}
