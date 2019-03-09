package cyx.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cyx.sell.dto.OrderDTO;
import cyx.sell.entity.OrderDetail;
import cyx.sell.enums.ResultEnum;
import cyx.sell.exception.SellException;
import cyx.sell.form.OrderForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象转换器，在属性上通过注解@JsonSerializer(using=xxx.class)使用
 */
public class OrderFormToOrderDTOConverter {
    private static final Logger log=LoggerFactory.getLogger(OrderFormToOrderDTOConverter.class);
    public static OrderDTO converter(OrderForm orderForm){
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        Gson gson=new Gson();
        TypeToken typeToken=new TypeToken<List<OrderDetail>>(){};
        List<OrderDetail> orderDetailList=new ArrayList<>();
        try {
            orderDetailList=gson.fromJson(orderForm.getItems(),typeToken.getType());
        }catch (Exception e){
            log.error("【json转换错误】string={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
