<p align="center">
hmtool-ext-interchan 基于hmtool & springboot 融合的工具包
</p>
<p align="center">
-- 主页：<a href="http://mhuang.tech/hmtool-ext-interchan">http://mhuang.tech/hmtool-ext-interchan</a>  --
</p>
<p align="center">
    -- QQ群①:<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=6703688b236038908f6c89b732758d00104b336a3a97bb511048d6fdc674ca01"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="hmtool官方交流群①" title="hmtool官方交流群①"></a>
</p>
---------------------------------------------------------------------------------------------------------------------------------------------------------

## 简介
hmtool-ext-interchan 是一个基于hmtool & springboot融合的工具包、将hmtool以及springboot无缝接入
## 结构
```
    - interchan-auth-common     权限拦截器工具包
    - interchan-autoconfiguation springboot自动注入工具包
    - interchan-core-common      核心处理工具包
    - interchan-payment-common   支付工具包
    - interchan-protocol-common  协议工具包
    - interchan-redis-common     基于redisTemplate简单封装工具包
    - interchan-redis-kafka-middle-common  kafka消费数据通过aop写入redis的工具包
    - interchan-starter-common   启动依赖工具包
    - interchan-wechat-common    微信封装工具包（包含小程序和微信公众号、原jwechat项目）
```
## demo案例

### springboot
[点击访问源码](http://gitee.com/hmtool/inter-boot-demo)
### springcloud
[点击访问源码](http://gitee.com/hmtool/inter-micro-demo)
> 注意
> hmtool只支持jdk1.8以上的版本