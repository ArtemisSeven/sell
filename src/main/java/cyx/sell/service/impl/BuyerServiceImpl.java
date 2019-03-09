package cyx.sell.service.impl;

import cyx.sell.dto.OrderDTO;
import cyx.sell.enums.ResultEnum;
import cyx.sell.exception.SellException;
import cyx.sell.service.BuyerService;
import cyx.sell.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuyerServiceImpl implements BuyerService {
    private static  final Logger log=LoggerFactory.getLogger(BuyerServiceImpl.class);

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO=checkOrderOwner(openid,orderId);
        if (orderDTO==null){
            log.error("【取消订单】订单不存在,orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId){
        OrderDTO orderDTO=orderService.findOne(orderId);
        if (orderDTO==null){
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("【查询订单】订单openid与买家openid不一致，无权查询.openid={},orderId={}",openid,openid);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
