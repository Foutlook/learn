package com.foutin.apache.httpclient;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author xingkai.fan
 * @date 2019/2/16 14:27
 */
public class DoGET {

    public static void doGetDemo() {
        //1.创建一个httpclient对象
        // 2. 创建http GET请求,这个不用加参数
//        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        //2. 创建带有参数的http get请求
        URI uri = null;
        try {
            uri = new URIBuilder("http://www.baidu.com/s").setParameter("wd", "java").build();
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(uri);
        // 3.执行请求
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet)) {
            // 4.判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                //5.请求体内容
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                //6.内容写入文件
                FileUtils.writeStringToFile(new File("D:\\devtest\\baidu1.html"), content, "UTF-8");
                System.out.println("内容长度：" + content.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        doGetDemo();
    }
}
