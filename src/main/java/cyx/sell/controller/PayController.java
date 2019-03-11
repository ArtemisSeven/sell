package cyx.sell.controller;

import com.lly835.bestpay.model.PayResponse;
import cyx.sell.dto.OrderDTO;
import cyx.sell.enums.ResultEnum;
import cyx.sell.exception.SellException;
import cyx.sell.service.PayService;
import cyx.sell.service.impl.OrderServiceImpl;
import cyx.sell.service.impl.PayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private PayServiceImpl payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId")String orderId, @RequestParam("returnUrl")String returnUrl,Map<String,Object> map){
        //查询订单
        OrderDTO orderDTO=orderService.findOne(orderId);
        if (orderDTO==null){
            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //发起支付
        PayResponse payResponse=payService.create(orderDTO);
        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);
        return new ModelAndView("pay/create",map);
    }

    /**
     * 接受支付后微信返回的异步通知，据此判断用户是否支付成功
     */
    @PostMapping("/notify")
    public ModelAndView notity(@RequestBody String notifyData){
        payService.notify(notifyData);
        //返回微信处理结果：跳转到success页面就可以成功告诉微信订单已处理完成，不要再发异步通知了
        return new ModelAndView("pay/success");
    }
}
