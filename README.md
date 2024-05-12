# OCR 项目 :sparkles:

## 📚 项目简介 :sunrise:

本项目是一个结合了DDD（领域驱动设计）架构理念的Java-Python跨界之作，旨在提供精准高效的OCR服务。

利用Python的[Paddle OCR](https://github.com/PaddlePaddle/PaddleOCR)库进行文字识别，该库以其高准确率和对复杂场景的适应性著称，能够轻松识别包括180°翻转在内的多种文本形式。

与此同时，Java部分采用了DDD架构，不仅加深了业务理解，也提升了代码的可维护性和扩展性。

前端则选用了Ant Design Pro，为用户提供流畅的交互体验。

前端代码仓库同样开源，欢迎您查阅和贡献：

- **前端项目地址**：[Laurc2004/ocr-frontend](https://github.com/Laurc2004/ocr-frontend) 使用Ant Design Pro构建，为用户带来极致的Web体验。

## 🔄 调用流程与功能说明 :gear:

### 💻 单体版本

- **公众号集成**：用户通过公众号发送图片或图片链接，根据引导即可启动OCR服务。
- **Python OCR引擎**：图片信息转发至Python后端，调用Paddle OCR进行文字识别。
- **Java处理逻辑**：识别结果被Java后端反序列化，提取关键信息并暂存于Redis（时效5分钟），利用线程池与Redission实现并发控制与流量限制。

### 🌐 微服务版本

- **Web界面操作**：用户访问Web界面，关注公众号后获取验证码登录，享受更丰富的功能。
- **用户认证与注册**：通过Spring Security实现安全验证，新用户自动注册。
- **多样化识别选项**：支持上传文件或输入URL，策略模式与工厂模式灵活选择处理策略，预留扩展接口如Base64识别。
- **结果输出定制**：提供专业模式输出详细坐标信息及文本识别分数，非专业模式则快速提取特定类型信息（如英文、车牌、身份证号等）。
- **支付与订单管理**：集成微信支付，采用EventBus解耦支付回调逻辑，配合定时任务处理异常订单。（前端暂时未做，本人微信商户到期）

## 🎨 功能展示 :framed_picture:

- **体验地址**：[在线体验](http://lixp.online:9888/)

- **截图预览**：
单体：
<img src="https://github.com/Laurc2004/ocr-ddd/assets/119660750/6e9a1c79-c792-4b6d-870a-09333161afeb" width="400">




微服务：





<img src="https://github.com/Laurc2004/ocr-ddd/assets/119660750/144790bb-698d-4a5f-addd-701667df94a4" width="400">

![image](https://github.com/Laurc2004/ocr-ddd/assets/119660750/4b65ddd4-9aae-4f26-a35f-19897c32ec19)

- **测试图片**：[示例图片](https://img0.pcauto.com.cn/pcauto/1812/25/14171817_paizhao.jpg)

## 🛠️ 软件架构 :wrench:

遵循DDD原则，项目分为四层：

- **触发层**：http、消息队列、定时任务等
- **应用层**：协调业务逻辑
- **领域层**：核心业务规则与实体
- **基础设施层**：数据库、缓存等

## 🔧 技术栈亮点 :key:

### Java & 中间件

- **Spring生态**：Spring Boot, MVC, Security, Data JPA
- **网络与数据**：Okhttp, Jackson, Mybatis, MySQL, Redis
- **API管理与文档**：Knife4j
- **开发辅助**：Lombok
- **消息传递**：EventBus（Guava）
- **配置与服务发现**：Nacos
- **流量控制**：Sentinel
- **分布式锁**：Redission

### Python

- **轻量级Web框架**：Flask
- **OCR库**：PaddleOCR

### 前端技术

- **React + Ant Design Pro**：构建现代Web应用
- **ArkTs**（规划中）：面向鸿蒙应用的TypeScript库
- **ReactNative**（规划中）：面向移动端

## 📖 安装部署教程 :book:

[1.0.0简易版教程](https://github.com/Laurc2004/ocr-ddd/wiki/%E5%AE%89%E8%A3%85%E5%92%8C%E8%BF%90%E8%A1%8C%E6%95%99%E7%A8%8B(1.0.0%E7%AE%80%E6%98%93%E7%89%88))
[2.0.0微服务版教程](https://github.com/Laurc2004/ocr-ddd/wiki/%E5%AE%89%E8%A3%85%E5%92%8C%E8%BF%90%E8%A1%8C%E6%95%99%E7%A8%8B(2.0.0%E5%BE%AE%E6%9C%8D%E5%8A%A1%E7%89%88))

欢迎Star🌟 和Fork该项目，您的反馈与贡献是我们不断进步的动力！
