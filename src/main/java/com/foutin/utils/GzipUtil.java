package com.foutin.utils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 图片压缩和解压
 *
 * @author xingkai.fan
 * @date 2019/2/27 15:17
 */
public class GzipUtil {

    /**
     * 压缩
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] gizp(byte[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(baos);
        gzip.write(data);
        gzip.finish();
        gzip.close();
        byte[] bytes = baos.toByteArray();
        baos.close();
        return bytes;
    }

    /**
     * 解压
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] ungizp(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        GZIPInputStream gzip = new GZIPInputStream(bais);
        byte[] bytes = new byte[1024];
        int num;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((num = gzip.read(bytes,0,bytes.length)) != -1){
            baos.write(bytes,0,num);
        }
        gzip.close();
        bais.close();
        byte[] ret = baos.toByteArray();
        baos.flush();
        baos.close();
        return ret;
    }


    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + File.separatorChar + "sources" + File.separatorChar + "concurrent.jpg";
        File file = new File(path);
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[fis.available()];
            int read = fis.read();
            fis.close();
            System.out.println("压缩前大小："+data.length);
            byte[] gizp = GzipUtil.gizp(data);
            System.out.println("压缩后的大小："+gizp.length);

            byte[] ungizp = GzipUtil.ungizp(gizp);
            System.out.println("解压后的大小："+ungizp.length);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
