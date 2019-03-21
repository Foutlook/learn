package com.foutin.apache.httpclient;

import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * 发送HTTPS请求
 *
 * @author xingkai.fan
 * @date 2019/2/16 16:36
 */
public class DoPOSTSSL {

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 5000;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        requestConfig = RequestConfig.custom()
                .setConnectTimeout(MAX_TIMEOUT)// 设置连接超时
                .setSocketTimeout(MAX_TIMEOUT)// 设置读取超时
                .setConnectionRequestTimeout(MAX_TIMEOUT) // 设置从连接池获取连接实例的超时
                .setStaleConnectionCheckEnabled(true)// 在提交请求之前 测试连接是否可用
                .build();
    }


    public static void doPostSsl() {
        // 创建Httpclient对象
        // 创建http POST请求
        URI uri;
        HttpGet httpGet = null;
        try {
//        HttpPost httpPost = new HttpPost("https://www.baidu.com/s");
            uri = new URIBuilder("https://www.zhihu.com/search").setParameter("q", "java").build();
            httpGet = new HttpGet(uri);
            // 设置2个post参数，一个是scope、一个是q
            /*List<NameValuePair> parameters = new ArrayList<>(4);*/
//        parameters.add(new BasicNameValuePair("scope", "project"));
//            parameters.add(new BasicNameValuePair("wd", "java"));
            // 构造一个form表单式的实体
//            UrlEncodedFormEntity formEntity = null;
//
//            formEntity = new UrlEncodedFormEntity(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 将请求实体设置到httpPost对象中
//        httpPost.setEntity(formEntity);
        //伪装浏览器
//        httpPost.setHeader("User-Agent",
//                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        httpGet.setHeader("user-agent:","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.90 Safari/537.36");

        // 执行请求
        try (CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
             CloseableHttpResponse response = httpclient.execute(httpGet)) {
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

    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();

            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

    public static void main(String[] args) {
        doPostSsl();
    }

}
