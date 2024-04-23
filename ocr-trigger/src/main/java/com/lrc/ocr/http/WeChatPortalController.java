package com.lrc.ocr.http;

import com.lrc.ocr.domain.wechat.model.entity.RequestMsgEntity;
import com.lrc.ocr.domain.wechat.model.entity.ResponseMsgEntity;
import com.lrc.ocr.domain.wechat.service.IWeChatValidateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wechat/protal/{appid}")
@Slf4j
@Api(tags = "微信公众号相关接口")
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
    @ApiOperation("微信公众号验签")

    public String validate(@PathVariable String appid,
                           @RequestParam(value = "signature", required = false) String signature,
                           @RequestParam(value = "timestamp", required = false) String timestamp,
                           @RequestParam(value = "nonce", required = false) String nonce,
                           @RequestParam(value = "echostr", required = false) String echostr) {

        return weChatValidateService.checkSign(appid, signature, timestamp, nonce, echostr);
    }

    /**
     * api
     * @param requestMsgEntity
     * @return
     */
    @PostMapping(produces = "application/xml; charset=UTF-8")
    @ApiOperation("微信公众号公众号逻辑处理")
    public ResponseMsgEntity post(@RequestBody RequestMsgEntity requestMsgEntity) {

        return weChatValidateService.getCode(requestMsgEntity);
    }
}
