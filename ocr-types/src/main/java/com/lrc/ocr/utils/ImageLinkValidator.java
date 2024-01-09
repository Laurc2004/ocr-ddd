package com.lrc.ocr.utils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageLinkValidator {
    private static final String regex = ".*\\.(jpg|jpeg|png|gif|bmp)$";
    private static final Pattern pattern = Pattern.compile(regex);
    /**
     * 判断给定的URL是否是图片链接
     *
     * @param img 要检查的URL
     * @return 如果URL是图片链接，返回true；否则返回false
     */

    public static boolean isImageLink(String img) {
        try {
            URL url = new URL(img);
            URLConnection uc = url.openConnection();
            InputStream in = uc.getInputStream();
            if (img.equalsIgnoreCase(uc.getURL().toString()))
                in.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}