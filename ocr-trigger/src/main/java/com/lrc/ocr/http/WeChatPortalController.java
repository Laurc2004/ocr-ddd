package com.lrc.ocr.http;

import com.lrc.ocr.domain.model.entity.ResponseMsgEntity;
import com.lrc.ocr.domain.service.IWeChatValidateService;
import com.lrc.ocr.domain.model.entity.RequestMsgEntity;
import com.lrc.ocr.prop.WeChatProp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wechat/protal/{appid}")
@Slf4j
public class WeChatPortalController {

    @Resource
    private IWeChatValidateService weChatValidateService;

    /**
     * 微信公众号验签
     *
     * @param appid
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String validate(@PathVariable String appid,
                           @RequestParam(value = "signature", required = false) String signature,
                           @RequestParam(value = "timestamp", required = false) String timestamp,
                           @RequestParam(value = "nonce", required = false) String nonce,
                           @RequestParam(value = "echostr", required = false) String echostr) {

        return weChatValidateService.checkSign(appid, signature, timestamp, nonce, echostr);
    }


    @PostMapping(produces = "application/xml; charset=UTF-8")
    public ResponseMsgEntity post(@RequestBody RequestMsgEntity requestMsgEntity) {

        return weChatValidateService.getStringByOcr(requestMsgEntity);
    }
}
