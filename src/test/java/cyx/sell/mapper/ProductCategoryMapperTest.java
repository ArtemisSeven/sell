package cyx.sell.mapper;

import cyx.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//添加webEnvironment是为了避免启动单元测试时报错Caused by: java.lang.IllegalStateException: javax.websocket.server.ServerContainer not available
public class ProductCategoryMapperTest {

    @Autowired
    ProductCategoryMapper categoryMapper;


    @Test
    public void insertByMap() {
        Map<String,Object> map=new HashMap<>();
        map.put("category_name","师姐最爱");
        map.put("category_type",101);
        int result=categoryMapper.insertByMap(map);
        Assert.assertEquals(1,result);
    }
    @Test
    public void insertByProductCategory() {
        ProductCategory category=new ProductCategory();
        category.setCategoryName("汤");
        category.setCategoryType(121);
        int result=categoryMapper.insertByProductCategory(category);
        Assert.assertEquals(1,result);
    }

    @Test
    public void findOneByCategoryType(){
        ProductCategory category=categoryMapper.findOneByCategoryType(120);
        Assert.assertNotNull(category);
    }
    @Test
    public void findOneByCategoryName(){
        List<ProductCategory> categoryList=categoryMapper.findOneByCategoryName("汤");
        Assert.assertNotEquals(0,categoryList.size());
    }

    @Test
    public void updateNameByType(){
        int result=categoryMapper.updateNameByType("饭",120);
        Assert.assertEquals(1,result);
    }

    @Test
    public void updateByEntity(){
        ProductCategory category=categoryMapper.findOneByCategoryType(120);
        category.setCategoryName("粥");
        int result=categoryMapper.updateByEntity(category);
        Assert.assertEquals(1,result);
    }

    @Test
    public void deleteByType(){
        int result=categoryMapper.deleteByType(120);
        Assert.assertEquals(1,result);
    }

}