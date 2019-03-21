package com.foutin.io.netty.marshalling;

import java.io.Serializable;

/**
 * 需要实现java的序列化接口
 * @author xingkai.fan
 * @date 2019/2/27 11:53
 */
public class Req implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String requestMessage;

    private byte[] attachment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Req [id=" + id + ", name=" + name + ", requestMessage=" + requestMessage + "]";
    }
}
