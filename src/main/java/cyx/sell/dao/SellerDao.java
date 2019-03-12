package cyx.sell.dao;

import cyx.sell.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerDao extends JpaRepository<Seller, String> {
    Seller findByOpenid(String openid);
}
