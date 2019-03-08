package cyx.sell.dao;

import cyx.sell.entity.Product;
import cyx.sell.enums.ProductStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product,String> {
    List<Product> findByProductStatus(Integer productStatus);
}
