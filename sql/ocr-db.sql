/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 80034 (8.0.34)
 Source Host           : localhost:3306
 Source Schema         : ocr-db

 Target Server Type    : MySQL
 Target Server Version : 80034 (8.0.34)
 File Encoding         : 65001

 Date: 11/05/2024 00:02:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ocr_result
-- ----------------------------
DROP TABLE IF EXISTS `ocr_result`;
CREATE TABLE `ocr_result`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `image_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '图片链接',
  `text_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT 'ocr识别的文字结果',
  `is_delete` tinyint NULL DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ocr_result
-- ----------------------------

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `openid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID；微信分配的唯一ID编码',
  `product_id` int NOT NULL COMMENT '商品ID',
  `product_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `order_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `order_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `order_status` tinyint(1) NOT NULL COMMENT '订单状态；0-创建完成、1-等待发货、2-发货完成、3-系统关单',
  `total_amount` decimal(10, 2) NOT NULL COMMENT '订单金额',
  `pay_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付地址；创建支付后，获得的URL地址',
  `pay_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额；支付成功后，以回调信息更新金额',
  `transaction_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '交易单号；支付成功后，回调信息的交易单号',
  `pay_status` tinyint(1) NULL DEFAULT NULL COMMENT '支付状态；0-等待支付、1-支付完成、2-支付失败、3-放弃支付',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_openid`(`openid` ASC) USING BTREE,
  INDEX `idx_order_status_pay_status_order_time`(`order_time` ASC, `order_status` ASC, `pay_status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (1, 'oqJEX6BhRAeeh0CbUS1ipURfjdso', 10001, '10次使用额度', '029048077293', '2024-05-10 10:34:27', 0, 0.01, NULL, NULL, NULL, 0, NULL);

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `product_id` int NOT NULL COMMENT '商品ID',
  `product_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `product_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品描述',
  `quota` int NOT NULL COMMENT '额度次数',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_id`(`product_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, 10001, '10次使用额度', '仅仅只是十次使用', 10, 0.01);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户名',
  `lines` bigint NULL DEFAULT NULL COMMENT '额度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'oqJEX6BhRAeeh0CbUS1ipURfjdso', 8887);

SET FOREIGN_KEY_CHECKS = 1;
