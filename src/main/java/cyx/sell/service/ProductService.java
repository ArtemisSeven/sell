package cyx.sell.service;

import cyx.sell.dto.CartDTO;
import cyx.sell.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product findOne(String id);
    List<Product> findUpAll();//所有在架商品
    Page<Product> findAll(Pageable pageable);//注意是spring里的分页
    Product save(Product product);
    Product onSale(String productId);//上架
    Product offSale(String productId);//下架
    void increaseStock(List<CartDTO> cartDTOList);//增库存
    void decreaseStock(List<CartDTO> cartDTOList);//减库存
}
