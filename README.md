# 基于ddd架构的ocr项目

#### 介绍 :sunny: 

本项目是一个基于ddd架构的涉及到Java和Python两种语言交互的项目，

Python不是本项目的重点，调用了飞桨的ocr的库，该库十分强大，识别文字准确度高，可识别180°翻转的文字。

Java在市面上mvc架构的项目较多，本项目打算一点一点扩大，所以准备采用ddd架构来实现。同时也是对该架构和其他技术栈进行练习。

#### 软件架构  :wrench: 

##### 软件架构说明

本项目采用了领域驱动设计（Domain-Driven Design，简称DDD）架构，这是一种软件开发方法，它主张将实现重点放在项目的领域和领域逻辑上，并强调使用一种通用的的语言来描述这些领域逻辑。在DDD架构中，软件被划分为多个层次，主要包括表示层（UI层）、应用层（Application Layer）、领域层（Domain Layer）和基础设施层（Infrastructure Layer）。

1. **表示层**：负责与用户交互，接收用户输入的图像数据和展示识别结果。
2. **应用层**：作为领域层与表示层之间的桥梁，应用层负责处理用户的请求，调用领域层的逻辑，并将结果返回给表示层。在OCR项目中，应用层可能包含对图像预处理、字符分割、识别和后处理等操作的封装。
3. **领域层**：是软件的核心，包含了业务逻辑。在OCR项目中，领域层可能包括图像处理、字符识别、语言模型等核心领域逻辑。领域层使用统一语言（Ubiquitous Language）来描述领域逻辑，确保领域逻辑的清晰和一致。
4. **基础设施层**：提供了软件运行所需的基础服务，例如数据库服务、文件存储服务、网络服务等。在OCR项目中，基础设施层可能包括对Python飞桨OCR库的调用、文件系统的操作、异步任务处理等。
   通过采用DDD架构，本项目能够将领域逻辑与用户界面、基础设施等其他层分离开来，使得领域逻辑更加纯粹和易于维护。同时，DDD架构也便于团队协作，减少误解，提高开发效率。

##### 采用技术栈  :key: 

###### Java

- [x] Spring、SpringMVC、Springboot
- [x] Okhttp
- [x] Jackson
- [x] Minio
- [x] Knife4j
- [x] Lombok
- [ ] Mybatis
- [ ] Mysql
- [ ] Spring security、Jwt
- [ ] Redis
- [ ] RabbitMQ
- [ ] Nacos
- [ ] Sentinel或Redission

###### Python

- [x] Flask
- [x] Paddle Ocr

###### 前端

- [ ] React
- [ ] Ant Design Pro
- [ ] ArkTs(鸿蒙)


#### 安装教程  :book: 

[安装和运行教程(1.0.0简易版) GitHub版本](https://github.com/Laurc2004/ocr-ddd/wiki/%E5%AE%89%E8%A3%85%E5%92%8C%E8%BF%90%E8%A1%8C%E6%95%99%E7%A8%8B(1.0.0%E7%AE%80%E6%98%93%E7%89%88))

[安装和运行教程(1.0.0简易版) Gitee版本](https://gitee.com/liu-ruichao/ocr-ddd/wikis/安装和运行教程(1.0.0简易版))
