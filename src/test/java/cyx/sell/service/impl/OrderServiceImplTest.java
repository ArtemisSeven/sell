package cyx.sell.service.impl;

import cyx.sell.dto.OrderDTO;
import cyx.sell.entity.OrderDetail;
import cyx.sell.enums.PayStatusEnum;
import cyx.sell.enums.OrderStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {
    private static final Logger log=LoggerFactory.getLogger(OrderServiceImplTest.class);

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "abc";

    private final String ORDER_ID = "15520578601";

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("cyx");
        orderDTO.setBuyerAddress("sz");
        orderDTO.setBuyerPhone("1234");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("2");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO result=orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】result={}",result);
        System.out.println(result.toString());
        Assert.assertEquals(ORDER_ID,result.getOrderId());
    }

    @Test
    public void findAllByOpenid() {
        PageRequest pageRequest=new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage=orderService.findAllByOpenid(BUYER_OPENID,pageRequest);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());

    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISH.getCode(), result.getOrderStatus());
    }

    @Test
    public void pay() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.pay(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest=new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage=orderService.findAll(pageRequest);
        Assert.assertTrue("查询所有订单",orderDTOPage.getTotalElements()>0);
    }
}