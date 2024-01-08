package com.lrc.ocr.domain.model.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 接收消息实体
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class RequestMsgEntity {
    /**开发者微信号*/
    private String ToUserName;
    /** 发送消息用户的openId */
    private String FromUserName;
    /** 消息创建时间 */
    private long CreateTime;
    /**消息类型*/
    private String MsgType;
    /** 消息ID，根据该字段来判重处理 */
    private long MsgId;
    /** 文本消息的消息体 */
    private String Content;
    /** 图片url */
    private String PicUrl;
}