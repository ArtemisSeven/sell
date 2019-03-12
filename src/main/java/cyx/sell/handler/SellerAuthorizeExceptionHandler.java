package cyx.sell.handler;

import cyx.sell.config.ProjectUrlConfig;
import cyx.sell.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * AOP的Advice
 */
@ControllerAdvice
public class SellerAuthorizeExceptionHandler {
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerSellerAuthorizeException(){
        String redirectUrl="redirect:"+projectUrlConfig.getWxOpenAuthorize() +"/sell/wechat/qrAuthorize?returnUrl="+projectUrlConfig.getSell()+"/sell/seller/login";
        return new ModelAndView(redirectUrl);
    }

}
