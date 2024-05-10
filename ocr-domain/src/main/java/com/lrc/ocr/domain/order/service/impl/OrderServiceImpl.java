package com.lrc.ocr.domain.order.service.impl;

import com.lrc.ocr.domain.order.model.aggregates.CreateOrderAggregate;
import com.lrc.ocr.domain.order.model.entity.OrderEntity;
import com.lrc.ocr.domain.order.model.entity.PayOrderEntity;
import com.lrc.ocr.domain.order.model.entity.ProductEntity;
import com.lrc.ocr.domain.order.model.valobj.OrderStatusVO;
import com.lrc.ocr.domain.order.model.valobj.PayStatusVO;
import com.lrc.ocr.domain.order.repository.IOrderRepository;
import com.lrc.ocr.prop.WechatPayProp;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl extends OrderService {


    @Resource
    private NativePayService payService;

    @Resource
    private WechatPayProp wechatPayProp;

    @Resource
    private IOrderRepository orderRepository;



    @Override
    protected OrderEntity saveOrder(String openid, ProductEntity productEntity) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderId(RandomStringUtils.randomNumeric(12));
        orderEntity.setOrderTime(new Date());
        orderEntity.setOrderStatus(OrderStatusVO.CREATE);
        orderEntity.setTotalAmount(productEntity.getPrice());

        CreateOrderAggregate createOrderAggregate = new CreateOrderAggregate().setOpenid(openid)
                .setOrder(orderEntity)
                .setProduct(productEntity);

        orderRepository.saveOrder(createOrderAggregate);
        return orderEntity;
    }

    @Override
    protected PayOrderEntity payOrder(String openid, String orderId, String productName, BigDecimal amountTotal) {
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(amountTotal.multiply(new BigDecimal(100)).intValue());
        request.setAmount(amount);
        request.setAppid(wechatPayProp.getAppid());
        request.setMchid(wechatPayProp.getMchId());
        request.setDescription(productName);
        request.setNotifyUrl(wechatPayProp.getNotifyUrl());
        request.setOutTradeNo(orderId);


        PrepayResponse prepay = payService.prepay(request);
        String payUrl = prepay.getCodeUrl();


        PayOrderEntity payOrderEntity = new PayOrderEntity()
                .setOpenid(openid)
                .setOrderId(orderId)
                        .setPayUrl(payUrl)
                                .setPayStatus(PayStatusVO.WAIT);


        // 更新订单支付信息
        orderRepository.updateOrderPayInfo(payOrderEntity);
        return payOrderEntity;
    }

    @Override
    public List<String> queryTimeoutCloseOrderList() {
        return orderRepository.queryTimeoutCloseOrderList();
    }

    @Override
    public boolean changeOrderClose(String orderId) {
        return orderRepository.changeOrderClose(orderId);
    }

    @Override
    public List<String> queryReplenishmentOrder() {
        return orderRepository.queryReplenishmentOrder();
    }

    @Override
    public List<String> queryNoPayNotifyOrder() {
        return orderRepository.queryNoPayNotifyOrder();
    }
}
