package cyx.sell.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WechatOpenConfig {
    @Autowired
    private WechatAccountConfig accountConfig;

    @Bean
    public WxMpServiceImpl wxOpenService(){//开放平台用的也是mpService
        WxMpServiceImpl wxOpenService=new WxMpServiceImpl();//所以new的是WxMpServiceImpl
        wxOpenService.setWxMpConfigStorage(wxOpenConfigStorage());
        return wxOpenService;
    }

    @Bean
    public WxMpConfigStorage wxOpenConfigStorage(){
        WxMpInMemoryConfigStorage wxOpenConfigStorage=new WxMpInMemoryConfigStorage();
        wxOpenConfigStorage.setAppId(accountConfig.getOpenAppId());//注意要用openAppId
        wxOpenConfigStorage.setSecret(accountConfig.getOpenAppSecret());
        return wxOpenConfigStorage;
    }
}
