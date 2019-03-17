# sell
基于springboot+redis的微信点餐系统后台

技术要点：
（1）微信授权、支付与退款<br/>
（2）卖家微信扫码登录、AOP身份验证<br/>
（3）设置token至redis实现分布式session、设置productId至redis实现分布式锁用于同步秒杀活动中商品库存、设置ResultVO至redis实现查询结果的缓存。<br/>
