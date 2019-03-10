package cyx.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;

@Component
@ConfigurationProperties(prefix = "wechat")
@Data
public class WechatAccountConfig {
    private String mpAppId;
    private String mpAppSecret;
    private String mchId;
    private String mchKey;
    private String keyPath;//商户证书存放的路径
    private String notifyUrl;//微信支付异步通知地址
}
