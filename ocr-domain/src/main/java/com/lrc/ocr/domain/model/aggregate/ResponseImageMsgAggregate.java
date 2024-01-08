package com.lrc.ocr.domain.model.aggregate;

import com.lrc.ocr.domain.model.entity.ResponseMsgEntity;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 图片消息响应实体类
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ResponseImageMsgAggregate {
    private ResponseMsgEntity responseMsgEntity;
    /** 图片媒体ID */
    @XmlElementWrapper(name = "Image")
    private String[] MediaId;
}