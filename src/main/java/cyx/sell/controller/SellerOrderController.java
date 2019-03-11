package cyx.sell.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import cyx.sell.dto.OrderDTO;
import cyx.sell.enums.ResultEnum;
import cyx.sell.exception.SellException;
import cyx.sell.service.OrderService;
import cyx.sell.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    /**
     * 订单列表
     *
     * @param page 从第1页开始
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "5") Integer size, Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findAll(pageRequest);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("pageSize", size);
        return new ModelAndView("order/list", map);
    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("【卖家端取消订单】查询不到订单,orderId={}", orderId);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId, Map<String, Object> map){
        OrderDTO orderDTO;
        try {
            orderDTO=orderService.findOne(orderId);
        }catch (SellException e){
            log.error("【卖家端订单详情】查询不到订单,orderId={}", orderId);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail",map);
    }

    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId, Map<String, Object> map) {
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (SellException e) {
            log.error("【卖家端完结订单】查询不到订单,orderId={}", orderId);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }
}
