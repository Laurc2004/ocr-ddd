package com.lrc.ocr.domain.service;

import com.lrc.ocr.constants.HttpConstants;
import com.lrc.ocr.domain.model.entity.RequestMsgEntity;
import com.lrc.ocr.domain.model.entity.ResponseMsgEntity;
import com.lrc.ocr.domain.model.vo.OcrTextVO;
import com.lrc.ocr.domain.service.manage.RedisLimiterManager;
import com.lrc.ocr.prop.WeChatProp;
import com.lrc.ocr.utils.ImageLinkValidator;
import com.lrc.ocr.utils.SignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 公众号验签
 */
@Service
@Slf4j
public class WeChatValidateServiceImpl implements IWeChatValidateService {

    @Resource
    private WeChatProp weChatProp;

    @Resource
    private IOcrService ocrService;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Resource
    private RedisLimiterManager redisLimiterManager;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public static final String KEY = "OPENID_";

    /**
     * 验签
     *
     * @param appid
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @Override
    public String checkSign(String appid, String signature, String timestamp, String nonce, String echostr) {

        boolean check = SignatureUtil.check(weChatProp.getToken(), signature, timestamp, nonce);

        try {
            log.info("微信公众号验签信息{}开始 [{}, {}, {}, {}]", appid, signature, timestamp, nonce, echostr);
            if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
                throw new IllegalArgumentException("请求参数非法，请核实!");
            }

            log.info("微信公众号验签信息{}完成 check：{}", appid, check);
            if (!check) {
                return null;
            }
            return echostr;
        } catch (Exception e) {
            log.error("微信公众号验签信息{}失败 [{}, {}, {}, {}]", appid, signature, timestamp, nonce, echostr, e);

        }

        return null;
    }

    /**
     * 接收公众号信息并调用ocr服务
     *
     * @param requestMsgEntity
     * @return
     */
    @Override
    public ResponseMsgEntity getStringByOcr(RequestMsgEntity requestMsgEntity) {
        log.info("接收微信公众号信息{}", requestMsgEntity);
        String fromUserName = requestMsgEntity.getFromUserName();
        String toUserName = requestMsgEntity.getToUserName();
        String msgType = requestMsgEntity.getMsgType();
        String picUrl = requestMsgEntity.getPicUrl();
        String content = requestMsgEntity.getContent();

        // 校验是否为图片或文字
        if (!msgType.equals(HttpConstants.IMAGE_TYPE) && !msgType.equals(HttpConstants.TEXT_TYPE)) {
            return createResponseMsgEntity(toUserName, fromUserName, "该类型消息暂不支持");
        }

        // 限流 一秒钟只能发一条信息
        if (!redisLimiterManager.doRateLimit(KEY + fromUserName)) {
            return createResponseMsgEntity(toUserName, fromUserName, "您发送太快");
        }

        // 判断类型是否为文本类型
        if (msgType.equals(HttpConstants.TEXT_TYPE)) {
            // 判断该文本是否为图片链接
            if (!ImageLinkValidator.isImageLink(content)) {
                return createResponseMsgEntity(toUserName, fromUserName, "该文本不为图片链接");
            }

            return getOcrResult(fromUserName, toUserName, content);

        }

        // 判断类型是否为图片类型
        if (msgType.equals(HttpConstants.IMAGE_TYPE)) {
            String reqContent = picUrl.trim();
            return getOcrResult(fromUserName, toUserName, reqContent);
        }


        return createResponseMsgEntity(toUserName, fromUserName, "发生未知错误，请联系管理员");
    }

    /**
     * 获取OCR处理的结果
     *
     * @param fromUserName 发送消息的用户名
     * @param toUserName   接收消息的用户名
     * @param content      消息内容
     * @return 返回响应消息实体
     */
    @NotNull
    private ResponseMsgEntity getOcrResult(String fromUserName, String toUserName, String content) {
        // 从Redis中获取结果
        String result = redisTemplate.opsForValue().get(content);
        if (result == null) {
            // 如果在Redis中找不到结果，则执行OCR任务
            doOcrTask(content);
            result = HttpConstants.NULL_RESULT;
        }

        // 如果结果仍然为空，则返回正在处理的消息
        if (HttpConstants.NULL_RESULT.equals(result)) {
            return createResponseMsgEntity(toUserName, fromUserName, "正在调用服务进行ocr处理中，请在五分钟内对我回复以下链接\n" + content.trim());
        }

        // 返回处理结果
        return createResponseMsgEntity(toUserName, fromUserName, result);
    }

    /**
     * 异步执行OCR任务
     *
     * @param reqContent 请求内容
     */
    private void doOcrTask(String reqContent) {
        threadPoolExecutor.execute(() -> {
            try {
                // 通过URL获取OCR文本
                OcrTextVO ocrText = ocrService.getTextByUrl(reqContent);
                String content = String.join("\n", ocrText.getOcrTextList());

                // 将结果存储到Redis中，并设置5分钟的过期时间
                redisTemplate.opsForValue().set(reqContent, content, 5, TimeUnit.MINUTES);
            } catch (Exception e) {
                log.error("OCR处理失败", e);

                // 如果处理失败，将错误信息存储到Redis中，并设置5分钟的过期时间
                redisTemplate.opsForValue().set(reqContent, "OCR处理失败，请重试", 5, TimeUnit.MINUTES);
            }
        });
    }

    /**
     * 创建微信消息响应对象
     *
     * @param toUserName   接收消息的用户名称
     * @param fromUserName 发送消息的用户名称
     * @param content      消息内容
     * @return 返回响应消息实体
     */
    private ResponseMsgEntity createResponseMsgEntity(String toUserName, String fromUserName, String content) {
        ResponseMsgEntity responseMsgEntity = new ResponseMsgEntity();
        responseMsgEntity.setFromUserName(toUserName);
        responseMsgEntity.setToUserName(fromUserName);
        responseMsgEntity.setMsgType(HttpConstants.TEXT_TYPE);
        responseMsgEntity.setContent(content);
        responseMsgEntity.setCreateTime(System.currentTimeMillis() / 1000L);
        log.info("发送信息{}", responseMsgEntity);
        return responseMsgEntity;
    }

}