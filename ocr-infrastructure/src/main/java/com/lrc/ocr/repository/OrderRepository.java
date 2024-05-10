package com.lrc.ocr.repository;

import com.lrc.ocr.dao.OrderMapper;
import com.lrc.ocr.dao.ProductMapper;
import com.lrc.ocr.dao.UserMapper;
import com.lrc.ocr.domain.order.model.aggregates.CreateOrderAggregate;
import com.lrc.ocr.domain.order.model.entity.OrderEntity;
import com.lrc.ocr.domain.order.model.entity.PayOrderEntity;
import com.lrc.ocr.domain.order.model.entity.ProductEntity;
import com.lrc.ocr.domain.order.model.entity.UnpaidOrderEntity;
import com.lrc.ocr.domain.order.model.valobj.OrderStatusVO;
import com.lrc.ocr.domain.order.model.valobj.PayStatusVO;
import com.lrc.ocr.domain.order.repository.IOrderRepository;
import com.lrc.ocr.po.Order;
import com.lrc.ocr.po.Product;
import com.lrc.ocr.po.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderRepository implements IOrderRepository {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ProductMapper productMapper;

    @Transactional
    @Override
    public void deliverGoods(String orderId) {
        Order order = orderMapper.queryByOrderId(orderId);
         // 变更发货状态
        order.setOrderStatus(OrderStatusVO.COMPLETED.getCode());
        orderMapper.updateByPrimaryKeySelective(order);
         // 查询商品额度
        int quota = productMapper.queryQuota(order.getProductId());
         // 增加用户额度
        User user = userMapper.queryUserByOpenid(order.getOpenid());
        user.setLines(user.getLines() + quota);
        userMapper.updateById(user);
    }

    @Override
    public boolean changeOrderPaySuccess(String orderId, String transactionId, BigDecimal totalAmount, Date date) {
        // 查询更新
        Order order = orderMapper.queryByOrderId(orderId);
        order.setTransactionId(transactionId)
                .setPayAmount(totalAmount)
                .setPayTime(date)
                .setOrderStatus(OrderStatusVO.COMPLETED.getCode())
                .setPayStatus(PayStatusVO.SUCCESS.getCode());

        int count = orderMapper.updateByPrimaryKeySelective(order);

        return count == 1;
    }

    @Override
    public List<ProductEntity> queryProduct() {
        List<Product> products = productMapper.selectAll();
        return products.stream()
                .map(this::productToProductEntity)
                .collect(Collectors.toList());
    }

    @Override
    public String getUserOpenidById(String id) {
        User user = userMapper.getById(Long.valueOf(id));
        return user.getOpenid();
    }

    @Override
    public UnpaidOrderEntity queryUnpaidOrder(String openid, Long productId) {
        Order order = new Order()
                .setOpenid(openid)
                .setProductId(productId)
                .setOrderStatus(OrderStatusVO.CREATE.getCode());
        order = orderMapper.queryOrder(order);
        if (order == null) return null;

        return new UnpaidOrderEntity()
                .setOpenid(openid)
                .setOrderId(order.getOrderId())
                .setTotalAmount(order.getTotalAmount())
                .setProductName(order.getProductName())
                .setPayUrl(order.getPayUrl())
                .setPayStatus(PayStatusVO.get(order.getPayStatus()));
    }

    @Override
    public ProductEntity queryProductByProductId(Long productId) {
        Product product = productMapper.queryByProductId(productId);
        return productToProductEntity(product);
    }

    @Override
    public void updateOrderPayInfo(PayOrderEntity payOrderEntity) {
        Order order = new Order().setOpenid(payOrderEntity.getOpenid())
                .setOrderId(payOrderEntity.getOrderId())
                .setPayUrl(payOrderEntity.getPayUrl())
                .setPayStatus(payOrderEntity.getPayStatus().getCode());

        orderMapper.updateOrderPayInfo(order);
    }

    @Override
    public void saveOrder(CreateOrderAggregate createOrderAggregate) {
        String openid = createOrderAggregate.getOpenid();
        OrderEntity orderEntity = createOrderAggregate.getOrder();
        ProductEntity productEntity = createOrderAggregate.getProduct();
        Order order = new Order();
        order.setOpenid(openid)
                .setProductId(productEntity.getProductId())
                .setProductName(productEntity.getProductName())
                .setOrderId(orderEntity.getOrderId())
                .setOrderTime(orderEntity.getOrderTime())
                .setOrderStatus(orderEntity.getOrderStatus().getCode())
                .setTotalAmount(orderEntity.getTotalAmount())
                .setPayStatus(PayStatusVO.WAIT.getCode());
        orderMapper.insertSelective(order);
    }

    @Override
    public List<String> queryTimeoutCloseOrderList() {
        return orderMapper.queryTimeoutCloseOrderList();
    }

    @Override
    public boolean changeOrderClose(String orderId) {
        Order order = new Order().setOrderId(orderId)
                .setPayStatus(PayStatusVO.WAIT.getCode())
                .setOrderStatus(OrderStatusVO.CLOSE.getCode());
        return orderMapper.updateOrderClose(order);
    }

    @Override
    public List<String> queryReplenishmentOrder() {
        return orderMapper.queryReplenishmentOrder();
    }

    @Override
    public List<String> queryNoPayNotifyOrder() {
        return orderMapper.queryNoPayNotifyOrder();
    }

    private ProductEntity productToProductEntity(Product product) {
        return new ProductEntity()
                .setProductId(product.getProductId())
                .setProductName(product.getProductName())
                .setProductDesc(product.getProductDesc())
                .setQuota(product.getQuota())
                .setPrice(product.getPrice());
    }
}
