# 基于ddd架构的ocr项目

## 介绍 :sunny: 

本项目是一个基于ddd架构的涉及到Java和Python两种语言交互的项目，

Python不是本项目的重点，调用了飞桨的ocr的库，该库十分强大，识别文字准确度高，可识别180°翻转的文字。

Java在市面上mvc架构的项目较多，本项目打算一点一点扩大，所以准备采用ddd架构来实现。同时也是对该架构和其他技术栈进行练习。

前端采用ant design pro，前端代码仓库 [Laurc2004/ocr-frontend (github.com)](https://github.com/Laurc2004/ocr-frontend)

单体版本

<img src="file:///C:\Users\lrc\Documents\Tencent Files\939688743\nt_qq\nt_data\Pic\2024-05\Ori\d07fff01f631bf18ed3dc92bb4e18be8.jpeg" alt="img" style="zoom: 33%;" />

微服务版本

<img src="file:///C:\Users\lrc\Documents\Tencent Files\939688743\nt_qq\nt_data\Pic\2024-05\Ori\c58ff4cd0f4b4e72ef302ff575c0d020.jpeg" alt="img" style="zoom:33%;" />

![image-20240510112007283](C:\Users\lrc\AppData\Roaming\Typora\typora-user-images\image-20240510112007283.png)

测试图片链接 https://img0.pcauto.com.cn/pcauto/1812/25/14171817_paizhao.jpg

![image-20240510112305521](C:\Users\lrc\AppData\Roaming\Typora\typora-user-images\image-20240510112305521.png)



## 软件架构  :wrench: 

### 软件架构说明

本项目采用了领域驱动设计（Domain-Driven Design，简称DDD）架构，这是一种软件开发方法，它主张将实现重点放在项目的领域和领域逻辑上，并强调使用一种通用的的语言来描述这些领域逻辑。在DDD架构中，软件被划分为多个层次，主要包括表示层（UI层）、应用层（Application Layer）、领域层（Domain Layer）和基础设施层（Infrastructure Layer）。



## 采用技术栈  :key: 

### Java及中间件

- [x] Spring、SpringMVC、Springboot
- [x] Okhttp
- [x] Jackson
- [x] Minio
- [x] Knife4j
- [x] Lombok
- [x] 微信公众开放平台
- [x] 微信支付
- [x] Mybatis
- [x] Mysql
- [x] Spring security、Jwt
- [x] Redis
- [x] ~~RabbitMQ~~ EventBus(Guava)
- [x] Nacos
- [ ] Sentinel
- [x] Redission

### Python

- [x] Flask
- [x] Paddle Ocr

### 前端

- [x] React
- [x] Ant Design Pro
- [ ] ArkTs(鸿蒙)


## 安装教程  :book: 

[安装和运行教程(1.0.0简易版) GitHub版本](https://github.com/Laurc2004/ocr-ddd/wiki/%E5%AE%89%E8%A3%85%E5%92%8C%E8%BF%90%E8%A1%8C%E6%95%99%E7%A8%8B(1.0.0%E7%AE%80%E6%98%93%E7%89%88))

[安装和运行教程(1.0.0简易版) Gitee版本](https://gitee.com/liu-ruichao/ocr-ddd/wikis/安装和运行教程(1.0.0简易版))
