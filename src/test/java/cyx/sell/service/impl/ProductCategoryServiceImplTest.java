package cyx.sell.service.impl;

import cyx.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl productCategoryService;

    @Test
    public void findOne() {
        ProductCategory result=productCategoryService.findOne(1);
        Assert.assertNotNull(result);
    }

    @Test
    public void findAll() {
        List<ProductCategory> results=productCategoryService.findAll();
        Assert.assertNotEquals(0,results.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> results=productCategoryService.findByCategoryTypeIn(Arrays.asList(1,2));
        Assert.assertNotEquals(0,results.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryId(3);
        productCategory.setCategoryType(3);
        productCategory.setCategoryName("男生专属");
        ProductCategory result=productCategoryService.save(productCategory);
        Assert.assertNotEquals(null,result);
    }
}