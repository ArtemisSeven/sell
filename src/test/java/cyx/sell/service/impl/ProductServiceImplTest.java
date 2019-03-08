package cyx.sell.service.impl;

import cyx.sell.entity.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        Product product=productService.findOne("111");
        Assert.assertNotNull(product);
    }

    @Test
    public void findUpAll() {
        List<Product> result=productService.findUpAll();
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    public void findAll() {
        PageRequest request=new PageRequest(0,2);
        Page<Product> result=productService.findAll(request);
        Assert.assertNotEquals(0,result.getTotalElements());
    }

    @Test
    public void save() {
        Product product=new Product();
        product.setProductId("111");
        product.setProductId("1");
        product.setCategoryType(1);
        product.setProductName("皮蛋粥");
        product.setProductPrice(new BigDecimal(1));
        product.setProductStock(100);
        product.setProductStatus(0);
        product.setProductDescription("haohe");
        product.setProductIcon("/xxx");
        Product result=productService.save(product);
        Assert.assertNotNull(result);
    }
}