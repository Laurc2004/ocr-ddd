package com.lrc.ocr.domain.order.repository;

import com.lrc.ocr.domain.order.model.aggregates.CreateOrderAggregate;
import com.lrc.ocr.domain.order.model.entity.PayOrderEntity;
import com.lrc.ocr.domain.order.model.entity.ProductEntity;
import com.lrc.ocr.domain.order.model.entity.UnpaidOrderEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IOrderRepository {
    void deliverGoods(String orderId);

    boolean changeOrderPaySuccess(String orderId, String transactionId, BigDecimal divide, Date date);

    List<ProductEntity> queryProduct();

    String getUserOpenidById(String id);

    UnpaidOrderEntity queryUnpaidOrder(String openid, Long productId);

    ProductEntity queryProductByProductId(Long productId);

    void updateOrderPayInfo(PayOrderEntity payOrderEntity);

    void saveOrder(CreateOrderAggregate createOrderAggregate);

    List<String> queryTimeoutCloseOrderList();

    boolean changeOrderClose(String orderId);

    List<String> queryReplenishmentOrder();

    List<String> queryNoPayNotifyOrder();
}
