package cyx.sell.controller;

import cyx.sell.VO.ResultVO;
import cyx.sell.converter.OrderFormToOrderDTOConverter;
import cyx.sell.dto.OrderDTO;
import cyx.sell.enums.ResultEnum;
import cyx.sell.exception.SellException;
import cyx.sell.form.OrderForm;
import cyx.sell.service.OrderService;
import cyx.sell.service.impl.BuyerServiceImpl;
import cyx.sell.service.impl.OrderServiceImpl;
import cyx.sell.utils.ResultVOUtils;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private BuyerServiceImpl buyerService;

    private static final Logger log=LoggerFactory.getLogger(BuyerOrderController.class);

    //创建订单
    @RequestMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO=OrderFormToOrderDTOConverter.converter(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        OrderDTO result=orderService.create(orderDTO);
        Map<String,String> map=new HashMap<>();
        map.put("orderId",result.getOrderId());
        return ResultVOUtils.success(map);
    }

    //订单列表
    @RequestMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid, @RequestParam(value="page",defaultValue = "0") Integer page, @RequestParam(value="size",defaultValue = "3") Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid不能为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest=new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage=orderService.findAllByOpenid(openid,pageRequest);
        return ResultVOUtils.success(orderDTOPage.getContent());
    }

    //订单详情
    @RequestMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid")String openid,@RequestParam("orderId")String orderId){
        OrderDTO orderDTO=buyerService.findOrderOne(openid,orderId);
        return ResultVOUtils.success(orderDTO);
    }

    //取消订单
    @RequestMapping("/cancel")
    public ResultVO<OrderDTO> cancel(@RequestParam("openid")String openid,@RequestParam("orderId")String orderId){
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtils.success();
    }

}
