## 微信点餐系统sell（服务端）

### 技术栈
> SpringBoot
Spring data jpa
MySQL
Redis
Maven
SL4J/logback
junit/springbootTest
nginx

### 功能介绍
> 买家端<br>订单模块：创建订单、查询订单列表、查看订单详情、取消订单<br/>商品模块：查看在架商品列表<br/>微信模块：微信授权、支付、申请退款

> 卖家端<br>订单模块：查看订单列表、查看订单详情、取消订单、完结订单<br/>商品模块：查看商品列表、上/下架商品、新增/更新商品、查看商品详情<br/>类目模块：新增/更新类目、查看类目列表、查看类目详情<br/>微信模块：微信扫码登录

### 技术要点：
>（1）微信授权、支付与退款
（2）卖家微信扫码登录、AOP身份验证
（3）分布式session、redis分布式锁、redis缓存
（4）使用第三方sdk进行微信开发

### 接口文档
> https://easydoc.xyz/?#/s/21266258
