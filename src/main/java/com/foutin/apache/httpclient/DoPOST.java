package com.foutin.apache.httpclient;

import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xingkai.fan
 * @date 2019/2/16 14:58
 */
public class DoPOST {
    public static void doPostDemo() {
        // 创建Httpclient对象
        // 创建http POST请求
        HttpPost httpPost = new HttpPost("http://www.oschina.net/search");
        // 设置2个post参数，一个是scope、一个是q
        List<NameValuePair> parameters = new ArrayList<>(0);
        parameters.add(new BasicNameValuePair("scope", "project"));
        parameters.add(new BasicNameValuePair("q", "java"));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = null;
        try {
            formEntity = new UrlEncodedFormEntity(parameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);
        //伪装浏览器
        httpPost.setHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");

        // 执行请求
        try (CloseableHttpClient httpclient = HttpClients.createDefault();
             CloseableHttpResponse response = httpclient.execute(httpPost)) {
            System.out.println(response.getStatusLine().getStatusCode() + "=====");
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                //内容写入文件
                FileUtils.writeStringToFile(new File("D:\\devtest\\oschina.html"), content, "UTF-8");
                System.out.println("内容长度：" + content.length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        doPostDemo();
    }
}
