package cyx.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "wechat")
@Data
public class WechatAccountConfig {
    private String mpAppId;//公众号的
    private String mpAppSecret;
    private String openAppId;//开放平台的
    private String openAppSecret;
    private String mchId;
    private String mchKey;
    private String keyPath;//商户证书存放的路径
    private String notifyUrl;//微信支付异步通知地址
    private Map<String,String> templateId;//微信模版消息id
}
