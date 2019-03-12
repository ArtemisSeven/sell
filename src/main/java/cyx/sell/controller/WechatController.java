package cyx.sell.controller;

import cyx.sell.config.ProjectUrlConfig;
import cyx.sell.config.WechatMpConfig;
import cyx.sell.enums.ResultEnum;
import cyx.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

@Controller//注意RestController是返回json格式的，这里要用controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * 构造网页授权url,获取code
     * 首先构造网页授权url，然后构成超链接让用户点击：
     * @param returnUrl
     * @return
     */
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl")String returnUrl){//这个returnUrl就是自己项目的地址，测试时可以使用https://www.baidu.com
        //1.配置 WxMpService
        String url=projectUrlConfig.getWxMpAuthorize()+"/sell/wechat/userInfo";//回调地址(微信服务器会附加code和state属性)
        String redirectUrl=wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_BASE, URLEncoder.encode(returnUrl));
        return "redirect:"+redirectUrl;
    }

    /**
     * 获得access token
     * 当用户同意授权后，会回调所设置的url并把authorization code传过来，然后用这个code获得access token，其中也包含用户的openid等信息
     * @param code
     * @param returnUrl
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code")String code,@RequestParam("state")String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken=new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR);
        }
        String openid=wxMpOAuth2AccessToken.getOpenId();
        return "redirect:"+returnUrl+"?openid="+openid;
    }

    /**
     * 微信扫码登陆获取code
     * @param returnUrl
     * @return
     */
    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl")String returnUrl){
        String url=projectUrlConfig.getWxOpenAuthorize()+"/sell/wechat/qrUserInfo";
        String redirectUrl=wxOpenService.buildQrConnectUrl(url,WxConsts.QrConnectScope.SNSAPI_LOGIN,URLEncoder.encode(returnUrl));
        return "redirect:"+redirectUrl;
    }

    @GetMapping("/qtUserInfo")
    public String qrUserInfo(@RequestParam("code")String code,@RequestParam("state")String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken=new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}",e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR);
        }
        String openid=wxMpOAuth2AccessToken.getOpenId();
        return "redirect:"+returnUrl+"?openid="+openid;
    }
}
