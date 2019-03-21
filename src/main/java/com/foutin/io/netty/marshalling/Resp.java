package com.foutin.io.netty.marshalling;

import java.io.Serializable;

/**
 * @author xingkai.fan
 * @date 2019/2/27 11:53
 */
public class Resp implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String responseMessage;

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

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @Override
    public String toString() {
        return "Resp [id=" + id + ", name=" + name + ", responseMessage=" + responseMessage + "]";
    }
}
