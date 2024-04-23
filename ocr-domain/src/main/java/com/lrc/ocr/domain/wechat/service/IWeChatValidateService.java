package com.lrc.ocr.domain.wechat.service;

import com.lrc.ocr.domain.wechat.model.entity.RequestMsgEntity;
import com.lrc.ocr.domain.wechat.model.entity.ResponseMsgEntity;

/**
 * 微信公众号验签
 */
public interface IWeChatValidateService {

    String checkSign(String appid, String signature, String timestamp, String nonce, String echostr);

    ResponseMsgEntity getCode(RequestMsgEntity requestMsgEntity);
}