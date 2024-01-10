package com.lrc.ocr.utils;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class ImageLinkValidator {

    /**
     * 判断给定的URL是否是图片链接
     *
     * @param img 要检查的URL
     * @return 如果URL是图片链接，返回true；否则返回false
     */

    public static boolean isImageLink(String img) {
        try {
            // 新建url和连接
            URL url = new URL(img);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间为3秒
            connection.setConnectTimeout(3000);
            // 设置读取超时时间为3秒
            connection.setReadTimeout(3000);
            // 立刻尝试连接，以启动计时
            connection.connect();
            // 检查是否成功连接
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 读取图像
                BufferedImage image = ImageIO.read(connection.getInputStream());
                log.info("解析图片:{}",image);
                return image != null;
            }
            // 如果响应码不是HTTP_OK，则认为不是图片链接
            return false;
        } catch (IOException e) {
            // 在读取图片时发生异常，可能不是图片链接
            return false;
        }
    }
}