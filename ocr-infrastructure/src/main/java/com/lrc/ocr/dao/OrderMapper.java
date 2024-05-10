package com.lrc.ocr.dao;


import com.lrc.ocr.po.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author lrc
* @description 针对表【order】的数据库操作Mapper
* @createDate 2024-05-08 17:55:53
* @Entity generator.com.lrc.ocr.po.Order
*/
@Mapper
public interface OrderMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order queryByOrderId(String orderId);

    Order queryOrder(Order order);

    void updateOrderPayInfo(Order order);

    List<String> queryTimeoutCloseOrderList();

    boolean updateOrderClose(Order order);

    List<String> queryReplenishmentOrder();

    List<String> queryNoPayNotifyOrder();
}
