package cyx.sell.service;

import cyx.sell.entity.Seller;

public interface SellerService {
    Seller findSellerByOpenid(String openid);
}
