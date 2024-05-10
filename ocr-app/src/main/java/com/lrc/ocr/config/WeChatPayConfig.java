package com.lrc.ocr.config;

import com.lrc.ocr.prop.WechatPayProp;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;

@Slf4j
@Configuration
public class WeChatPayConfig {
    @Resource
    private WechatPayProp wechatPayProp;

    @Bean
    public NativePayService buildNativePayService() {
        // 支付配置
        Config config = new RSAAutoCertificateConfig.Builder()
                .merchantId(wechatPayProp.getMchId())
                .privateKeyFromPath(getFilePath(wechatPayProp.getPrivateKeyFilePath()))
                .merchantSerialNumber(wechatPayProp.getMchSerialNo())
                .apiV3Key(wechatPayProp.getApiV3Key())
                .build();

        // NativePay 支付服务
        return new NativePayService.Builder().config(config).build();
    }

    @Bean
    public NotificationConfig buildNotificationConfig() {
        return new RSAAutoCertificateConfig.Builder()
                .merchantId(wechatPayProp.getMchId())
                .privateKeyFromPath(getFilePath(wechatPayProp.getPrivateKeyFilePath()))
                .merchantSerialNumber(wechatPayProp.getMchSerialNo())
                .apiV3Key(wechatPayProp.getApiV3Key())
                .build();
    }

    @Bean
    public NotificationParser buildNotificationParser(NotificationConfig notificationConfig) {
        return new NotificationParser(notificationConfig);
    }

    public static String getFilePath(String classFilePath) {
        String filePath = "";
        try {
            String templateFilePath = "tempfiles/classpathfile/";
            File tempDir = new File(templateFilePath);
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            String[] filePathList = classFilePath.split("/");
            String checkFilePath = "tempfiles/classpathfile";
            for (String item : filePathList) {
                checkFilePath += "/" + item;
            }
            File tempFile = new File(checkFilePath);
            if (tempFile.exists()) {
                filePath = checkFilePath;
            } else {
                //解析
                ClassPathResource classPathResource = new ClassPathResource(classFilePath);
                InputStream inputStream = classPathResource.getInputStream();
                checkFilePath = "tempfiles/classpathfile";
                for (int i = 0; i < filePathList.length; i++) {
                    checkFilePath += "/" + filePathList[i];
                    if (i == filePathList.length - 1) {
                        //文件
                        File file = new File(checkFilePath);
                        if (!file.exists()) {
                            FileUtils.copyInputStreamToFile(inputStream, file);
                        }
                    } else {
                        //目录
                        tempDir = new File(checkFilePath);
                        if (!tempDir.exists()) {
                            tempDir.mkdirs();
                        }
                    }
                }
                inputStream.close();
                filePath = checkFilePath;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

}
