package cyx.sell.dao;

import cyx.sell.entity.Product;
import cyx.sell.enums.ProductStatusEnum;
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
public class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void save(){
        Product product=new Product();
        product.setProductId("111");
        product.setProductId("1");
        product.setCategoryType(1);
        product.setProductName("皮蛋粥");
        product.setProductPrice(new BigDecimal(1));
        product.setProductStock(100);
        product.setProductStatus(0);
        product.setProductDescription("好喝");
        product.setProductIcon("/xxx");
        Product result=productDao.save(product);
        Assert.assertNotNull(result);

    }
    @Test
    public void findByProductStatus() {
        List<Product> result=productDao.findByProductStatus(ProductStatusEnum.UP.getCode());
        Assert.assertNotEquals(0,result.size());
    }
}