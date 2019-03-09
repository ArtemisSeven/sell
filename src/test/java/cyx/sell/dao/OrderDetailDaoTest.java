package cyx.sell.dao;

import cyx.sell.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Test
    public void save(){
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDetailId("1");
        orderDetail.setOrderId("1");
        orderDetail.setProductIcon("http://sss");
        orderDetail.setProductId("1");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(1));
        orderDetail.setProductQuantity(2);
        OrderDetail result=orderDetailDao.save(orderDetail);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList=orderDetailDao.findByOrderId("1");
        Assert.assertNotEquals(0,orderDetailList.size());
    }
}