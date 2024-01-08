package com.lrc.ocr.domain.service;

import com.lrc.ocr.constants.HttpConstants;
import com.lrc.ocr.domain.model.entity.RequestMsgEntity;
import com.lrc.ocr.domain.model.entity.ResponseMsgEntity;
import com.lrc.ocr.domain.model.vo.OcrTextVO;
import com.lrc.ocr.prop.WeChatProp;
import com.lrc.ocr.utils.sdk.SignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.stream.Collectors;

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

    /**
     * 验签
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
        // 对传入的信息进行校验，如果不是图片立刻停止响应
        if (!msgType.equals(HttpConstants.IMAGE_TYPE) || StringUtils.isBlank(picUrl)){
            // 返回封装结果
            ResponseMsgEntity responseMsgEntity = new ResponseMsgEntity();

            responseMsgEntity.setFromUserName(toUserName);
            responseMsgEntity.setToUserName(fromUserName);
            responseMsgEntity.setMsgType(HttpConstants.TEXT_TYPE);
            responseMsgEntity.setContent("发送图片给我即可进行ocr识别");
            responseMsgEntity.setCreateTime(System.currentTimeMillis() / 1000L);
            return responseMsgEntity;
        }

        // 调用url服务
        OcrTextVO ocrText = ocrService.getTextByUrl(picUrl);
        // 对字符串进行拼接
        String content = String.join("\n", ocrText.getOcrTextList());
//        StringBuilder builder = new StringBuilder();
//        for (String text : ocrText.getOcrTextList()) {
//            builder.append(text).append("\n");
//        }
//
//        String content = builder.toString();
        // 返回封装结果
        ResponseMsgEntity responseMsgEntity = new ResponseMsgEntity();

        responseMsgEntity.setFromUserName(toUserName);
        responseMsgEntity.setToUserName(fromUserName);
        responseMsgEntity.setMsgType(HttpConstants.TEXT_TYPE);
        responseMsgEntity.setContent(content);
        responseMsgEntity.setCreateTime(System.currentTimeMillis() / 1000L);

        return responseMsgEntity;
    }

}