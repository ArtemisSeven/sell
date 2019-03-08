package cyx.sell.dao;

import cyx.sell.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer> {//<表对应的类，类的主键类型>
    //返回指定categoryType的所有数据，注意函数名的命名是规定的
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
