package cyx.sell.mapper;
import cyx.sell.entity.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ProductCategoryMapper {
    //插入第一种方式：通过map,注意#{xxx需要与map中的key一样
    @Insert("insert into product_category (category_name,category_type) values (#{category_name,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})")
    int insertByMap(Map<String,Object> map);


    //插入第二种方式：通过实体类,注意#{xxx需要与实体类的属性一样
    @Insert("insert into product_category (category_name,category_type) values (#{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER})")
    int insertByProductCategory(ProductCategory productCategory);

    //查询结果只有一个
    @Select("select * from product_category where category_type=#{categoryType}")
    @Results({
            @Result(column = "category_name",property = "categoryName"),
            @Result(column = "category_type",property = "categoryType"),
    })
    ProductCategory findOneByCategoryType(Integer categoryType);

    //查询结果有多个
    @Select("select * from product_category where category_name=#{categoryName}")
    @Results({
            @Result(column = "category_name",property = "categoryName"),
            @Result(column = "category_type",property = "categoryType"),
    })
    List<ProductCategory> findOneByCategoryName(String categoryName);

    //更新第一种方式：参数是字段，注意@Param
    @Update("update product_category set category_name=#{categoryName} where category_type=#{categoryType}")
    int updateNameByType(@Param("categoryName") String categoryName,@Param("categoryType") Integer categoryType);

    //更新第二种方式：参数是实体类
    @Update("update product_category set category_name=#{categoryName} where category_type=#{categoryType}")
    int updateByEntity(ProductCategory productCategory);

    @Delete("delete from product_category where category_type=#{categoryType}")
    int deleteByType(Integer categoryType);

}
