package cyx.sell.controller;

import cyx.sell.dto.OrderDTO;
import cyx.sell.enums.ResultEnum;
import cyx.sell.exception.SellException;
import cyx.sell.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/create")
    public void create(@RequestParam("orderId")String orderId,@RequestParam("returnTrl")String returnUrl){
        //查询订单
        OrderDTO orderDTO=orderService.findOne(orderId);
        if (orderDTO==null){
            throw  new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //发起支付

    }
}
