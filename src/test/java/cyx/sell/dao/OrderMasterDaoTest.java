package cyx.sell.dao;

import cyx.sell.entity.OrderMaster;
import cyx.sell.entity.PayStatusEnum;
import cyx.sell.enums.OrderStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {
    @Autowired
    private OrderMasterDao orderMasterDao;

    private static final String OPENID="abc";

    @Test
    public void save() {
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("1");
        orderMaster.setBuyerName("cyx");
        orderMaster.setBuyerPhone("123");
        orderMaster.setBuyerAddress("sz");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(100));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        OrderMaster result=orderMasterDao.save(orderMaster);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByBuyerOpenid() {
        PageRequest request=new PageRequest(0,2);
        Page<OrderMaster> result=orderMasterDao.findByBuyerOpenid(OPENID,request);
        System.out.println(result.getTotalElements());
    }
}