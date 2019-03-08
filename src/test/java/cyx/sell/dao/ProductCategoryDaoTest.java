package cyx.sell.dao;

import cyx.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {
    @Autowired
    ProductCategoryDao productCategoryDao;
    @Test
    public void findOneTest(){
        Optional<ProductCategory> productCategorys=productCategoryDao.findById(1);
        ProductCategory productCategory=productCategorys.get();
        System.out.println(productCategory.toString());
    }
    @Test
//    @Transactional//插入的数据会在测试后自动删除，保持数据库是干净的
    public void saveTest(){
        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryId(2);
        productCategory.setCategoryType(2);
        productCategory.setCategoryName("男生最爱");
        ProductCategory result=productCategoryDao.save(productCategory);
        Assert.assertNotNull(result);
    }
    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> categoryType= Arrays.asList(1,2);
        List<ProductCategory> result=productCategoryDao.findByCategoryTypeIn(categoryType);
        Assert.assertNotEquals(0,result.size());//查出来的结果数!=0则算成功
    }
}