package com.lrc.ocr.domain.service;

import com.lrc.ocr.domain.model.entity.RequestMsgEntity;
import com.lrc.ocr.domain.model.entity.ResponseMsgEntity;

/**
 * 微信公众号验签
 */
public interface IWeChatValidateService {

    String checkSign(String appid, String signature, String timestamp, String nonce, String echostr);

    ResponseMsgEntity getStringByOcr(RequestMsgEntity requestMsgEntity);
}