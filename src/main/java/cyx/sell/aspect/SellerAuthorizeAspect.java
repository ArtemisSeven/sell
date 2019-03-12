package cyx.sell.aspect;

import cyx.sell.constant.CookieConstant;
import cyx.sell.constant.RedisConstant;
import cyx.sell.exception.SellerAuthorizeException;
import cyx.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * AOP实现身份验证
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * cyx.sell.controller.Seller*.*(..))&&!execution(public * cyx.sell.controller.SellerUserController.*(..)")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        ServletRequestAttributes attributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        //查询cookie
        Cookie cookie=CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie==null){
            log.warn("【登陆校验】cookie中查不到token");
            throw new SellerAuthorizeException();//再编写用@ControlerAdvice注解的异常处理类
        }
        String token=redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if (token==null){
            log.warn("【登陆校验】redis中查不到token");
            throw new SellerAuthorizeException();
        }

    }

}
