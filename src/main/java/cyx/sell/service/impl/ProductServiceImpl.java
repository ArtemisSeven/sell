package cyx.sell.service.impl;

import cyx.sell.dao.ProductDao;
import cyx.sell.dto.CartDTO;
import cyx.sell.entity.Product;
import cyx.sell.enums.ProductStatusEnum;
import cyx.sell.enums.ResultEnum;
import cyx.sell.exception.SellException;
import cyx.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
