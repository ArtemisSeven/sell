package cyx.sell.controller;

import cyx.sell.config.ProjectUrlConfig;
import cyx.sell.constant.CookieConstant;
import cyx.sell.constant.RedisConstant;
import cyx.sell.entity.Seller;
import cyx.sell.enums.ResultEnum;
import cyx.sell.service.impl.SellerServiceImpl;
import cyx.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerUserController {
    @Autowired
    private SellerServiceImpl sellerService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private StringRedisTemplate redisTemplate;//由于目前只需要往redis写String类型的值，所以只用到StringRedisTemplate

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid, Map<String, Object> map, HttpServletResponse response) {
        //查看数据库seller表是否有此用户
        Seller seller = sellerService.findSellerByOpenid(openid);
        if (seller == null) {
            log.error("【Seller不存在】失败，openid={}", openid);
            map.put("msg", ResultEnum.LOGIN_FAIL);
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
        //设置token至redis
        String token = String.format(RedisConstant.TOKEN_PREFIX, UUID.randomUUID().toString());
        Integer expire = RedisConstant.EXPIRE;
        redisTemplate.opsForValue().set(token, openid, expire, TimeUnit.SECONDS);

        //设置token到cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list");//注意这里要用绝对地址
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,HttpServletResponse response,Map<String,Object> map){
        //从request里获取cookie
        Cookie cookie=CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie!=null) {
            String token = String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue());
            //清除redis
            redisTemplate.opsForValue().getOperations().delete(token);
            //清除cookie
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ResultEnum.LOGOUT_SUCCESS);
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }
}
