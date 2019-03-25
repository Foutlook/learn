package com.foutin.websocket.service;



/**
 * @author xingkai.fan
 * @date 2019/3/25
 */

public interface WebService {
    /**
     * 编解码
     * @param code
     * @return
     */
    String encode(String code);

    String decode(String code);
}
