package com.lrc.ocr.domain.order.service.impl;

import com.lrc.ocr.domain.order.model.entity.OrderEntity;
import com.lrc.ocr.domain.order.model.entity.PayOrderEntity;
import com.lrc.ocr.domain.order.model.entity.ProductEntity;
import com.lrc.ocr.domain.order.model.entity.UnpaidOrderEntity;
import com.lrc.ocr.domain.order.model.valobj.PayStatusVO;
import com.lrc.ocr.domain.order.repository.IOrderRepository;
import com.lrc.ocr.domain.order.service.IOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public abstract class OrderService implements IOrderService {
    @Resource
    private IOrderRepository orderRepository;
    @Override
    public void deliverGoods(String orderId) {
        orderRepository.deliverGoods(orderId);
    }

    @Override
    public boolean changeOrderPaySuccess(String orderId, String transactionId, BigDecimal divide, Date date) {
        return orderRepository.changeOrderPaySuccess(orderId, transactionId, divide,date);
    }

    @Override
    public List<ProductEntity> listProduct() {
        return orderRepository.queryProduct();
    }

    @Override
    public String createOrder(Long productId) {
        // 获取openid
        String id = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String openid = orderRepository.getUserOpenidById(id);
        // 查询未支付的订单
        UnpaidOrderEntity unpaidOrderEntity = orderRepository.queryUnpaidOrder(openid,productId);
        if (unpaidOrderEntity != null
                && unpaidOrderEntity.getPayStatus().equals(PayStatusVO.WAIT)
                && StringUtils.isNotEmpty(unpaidOrderEntity.getPayUrl())){
            return unpaidOrderEntity.getPayUrl();
            // payUrl为空的情况后
        } else if (unpaidOrderEntity != null && StringUtils.isEmpty(unpaidOrderEntity.getPayUrl())) {
            // 创建订单
            PayOrderEntity payOrderEntity = payOrder(openid, unpaidOrderEntity.getOrderId(), unpaidOrderEntity.getProductName(), unpaidOrderEntity.getTotalAmount());
            return payOrderEntity.getPayUrl();
        }

        // 查询商品
        ProductEntity productEntity = orderRepository.queryProductByProductId(productId);


        // 保存订单
        OrderEntity orderEntity = saveOrder(openid, productEntity);
        // 提交订单
        PayOrderEntity payOrderEntity = payOrder(openid, orderEntity.getOrderId(), productEntity.getProductName(), orderEntity.getTotalAmount());

        return payOrderEntity.getPayUrl();
    }

    protected abstract OrderEntity saveOrder(String openid, ProductEntity productEntity);

    protected abstract PayOrderEntity payOrder(String openid, String orderId, String productName, BigDecimal amountTotal);
}
