package cyx.sell.service.impl;

import cyx.sell.dao.ProductDao;
import cyx.sell.dto.CartDTO;
import cyx.sell.entity.Product;
import cyx.sell.enums.ProductStatusEnum;
import cyx.sell.enums.ResultEnum;
import cyx.sell.exception.SellException;
import cyx.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "productOne")//加了这个注解就不用每个方法 都写cacheNames了
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

//    @Cacheable(key="456")//
    @Override
    public Product findOne(String id) {
        Optional<Product> optionalProduct=productDao.findById(id);
        Product product=optionalProduct.isPresent()?optionalProduct.get():null;
        return product;
    }

//    @CachePut(key = "456")//由于这两个方法返回的类型都是Product，并且可以序列化，所以这里可以使用@CachePut
    @Override
    public Product save(Product product) {
        return productDao.save(product);
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
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            Product product=productDao.getOne(cartDTO.getProductId());
            if (product==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result=product.getProductStock()+cartDTO.getProductQuantity();
            product.setProductStock(result);
            productDao.save(product);
        }
    }

    @Override
    @Transactional//由于参数是一个集合，所以要用事务，要么成功要么不成功
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            Product product=this.findOne(cartDTO.getProductId());
            if (product==null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer stock=product.getProductStock()-cartDTO.getProductQuantity();
            if (stock<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            product.setProductStock(stock);
            System.out.println("haha"+stock+" "+product.getProductStock());
            this.save(product);
        }
    }

    @Override
    public Product onSale(String productId) {
        Product productInfo = findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return save(productInfo);
    }

    @Override
    public Product offSale(String productId) {
        Product productInfo = findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return save(productInfo);
    }
}
