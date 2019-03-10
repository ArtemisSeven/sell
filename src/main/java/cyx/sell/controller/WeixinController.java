package cyx.sell.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weixin")
public class WeixinController {
    private static final Logger log=LoggerFactory.getLogger(WeixinController.class);
    private static final String APPID="wx2aef3b3062efcb09";
    private static final String SECRET="bd15086d8359818808c0a53a00f9d7b0";
    @GetMapping("/auto")
    public void auto(@RequestParam("code")String code){
        //用户访问某个url(里面的回调地址就是这个函数的地址)，微信服务器会会将请求重定向到这里，并且附带code参数
        log.info("auto..code={}",code);
        //得到code之后，拼接APPID等信息形成url，向微信服务器发送请求，会得到微信服务器响应信息(带有access_token)
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APPID+"&secret="+SECRET+"&code="+code+"&grant_type=authorization_code";
        RestTemplate restTemplate=new RestTemplate();
        String response=restTemplate.getForObject(url,String.class);//设置返回的类型为String
        log.info("response={}",response);
//        String REFRESH_TOKEN="";
        //刷新access_token(如果有必要)
//        String refreshTokenUrl="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+APPID+"&grant_type=refresh_token&refresh_token="+REFRESH_TOKEN;

    }
}
