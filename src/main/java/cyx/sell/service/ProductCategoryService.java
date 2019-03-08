package cyx.sell.service;

import cyx.sell.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    ProductCategory findOne(int id);
    List<ProductCategory> findAll();
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypes);
    ProductCategory save(ProductCategory productCategory);
}
