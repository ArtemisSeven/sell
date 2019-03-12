package cyx.sell.service.impl;

import cyx.sell.dao.ProductCategoryDao;
import cyx.sell.entity.ProductCategory;
import cyx.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    ProductCategoryDao productCategoryDao;
    @Override
    public ProductCategory findOne(int id) {
        Optional<ProductCategory> categoryOptional=productCategoryDao.findById(id);
        ProductCategory category=categoryOptional.isPresent()?categoryOptional.get():null;
        return category;
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryDao.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypes) {
        return productCategoryDao.findByCategoryTypeIn(categoryTypes);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryDao.save(productCategory);
    }
}
