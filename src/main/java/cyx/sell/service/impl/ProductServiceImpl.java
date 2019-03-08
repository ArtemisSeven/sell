package cyx.sell.service.impl;

import cyx.sell.dao.ProductDao;
import cyx.sell.entity.Product;
import cyx.sell.enums.ProductStatusEnum;
import cyx.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product findOne(String id) {
        return productDao.findById(id).get();
    }

    @Override
    public List<Product> findUpAll() {
        return productDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productDao.findAll(pageable);
    }

    @Override
    public Product save(Product product) {
        return productDao.save(product);
    }
}
