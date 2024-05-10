package com.lrc.ocr.domain.order.service;

import com.lrc.ocr.domain.order.model.entity.ProductEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IOrderService {
    void deliverGoods(String orderId);

    boolean changeOrderPaySuccess(String orderId, String transactionId, BigDecimal divide, Date parse);

    List<ProductEntity> listProduct();

    String createOrder(Long productId);

    List<String> queryTimeoutCloseOrderList();

    boolean changeOrderClose(String orderId);

    List<String> queryReplenishmentOrder();

    List<String> queryNoPayNotifyOrder();
}
